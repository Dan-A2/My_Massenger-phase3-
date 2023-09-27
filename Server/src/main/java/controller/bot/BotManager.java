package controller.bot;

import database.Database;
import database.bot.BotDatabase;
import ir.sharif.ap.phase3.bot.BotResponse;
import ir.sharif.ap.phase3.model.main.Chat;
import ir.sharif.ap.phase3.model.main.GroupChat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.Loop;
import ir.sharif.ap.phase3.util.MessageStatus;
import logic.ChatController;
import logic.GroupController;
import logic.SendingMassageController;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

public class BotManager extends Thread {

    private final User botUser;
    private final String path;
    private Class<?> botClass;

    public BotManager(User botUser, String path) {
        this.botUser = botUser;
        this.path = path;
    }

    @Override
    public void run() {
        Loop loop = new Loop(0.2, this::reloadBot);
        loop.start();
    }

    private void reloadBot() {
        try {
            File file = new File(path);
            URL url = file.getParentFile().toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
            String className = file.getName().substring(0, file.getName().lastIndexOf("."));
            botClass = urlClassLoader.loadClass(className);
            String data = BotDatabase.loadBotFields(botUser.getId());
            if (data != null) {
                System.out.println("setting fields to " + data);
                botClass.getMethod("setFields", String.class).invoke(null, BotDatabase.loadBotFields(botUser.getId()));
            }
            answerMessage();
            Thread.sleep(2000);
            System.out.println("getting fields");
            BotDatabase.saveBotFields(botUser.getId(), (String) botClass.getMethod("getFields").invoke(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void answerMessage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("answering");
        for (Chat chat : botUser.getMyChats()) {
            if (chat.getUser1Id() == botUser.getId() && chat.getUser1UnseenMassages() > 0) {
                checkMessages(chat);
            } else if (chat.getUser2Id() == botUser.getId() && chat.getUser2UnseenMassages() > 0){
                checkMessages(chat);
            }
        }
    }

    private void checkMessages(Chat chat) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (int i = chat.getMessages().size()-1; i >= 0; i--) {
            if (chat.getMessages().get(i).getStatus() == MessageStatus.Sent || chat.getMessages().get(i).getStatus() == MessageStatus.Delivered) {
                Message m = chat.getMessages().get(i);
                m.setStatus(MessageStatus.Seen);
                Method method = botClass.getMethod("respond", String.class, int.class);
                BotResponse response = (BotResponse) method.invoke(null, m.getText(), m.getSender().getId());
                System.out.println(response);
                visitResponse(response);
                Database.update(chat.getMessages().get(i));
            } else {
                break;
            }
        }
    }

    private void visitResponse(BotResponse response) {
        if (response == null) { System.out.println("null bot response"); return;}
        int sendToStatus = Integer.parseInt(response.getSendTo());
        if (sendToStatus == 1) { // massage in pv
            String receivers = response.getReceiver();
            if (receivers.contains(",")) {
                List<Integer> receiversId = new LinkedList<>();
                while (receivers.contains(",")) {
                    int index = receivers.indexOf(',');
                    receiversId.add(Integer.parseInt(receivers.substring(0, index)));
                    receivers = receivers.substring(index+1);
                }
                for (Integer id : receiversId) {
                    User user = Database.get(id, User.class);
                    SendingMassageController controller = new SendingMassageController();
                    controller.sendMassage1(botUser, user, response.getMessage(), false, null);
                }
            } else {
                User user = Database.get(Integer.parseInt(receivers), User.class);
                SendingMassageController controller = new SendingMassageController();
                controller.sendMassage1(botUser, user, response.getMessage(), false, null);
            }
        } else { // massage in group
            GroupChat group = Database.get(Integer.parseInt(response.getReceiver()), GroupChat.class);
            GroupController controller = new GroupController();
            Message message = new Message(response.getMessage(), botUser);
            Database.save(message);
            controller.sendMassage(group, message, botUser);
        }
    }
}