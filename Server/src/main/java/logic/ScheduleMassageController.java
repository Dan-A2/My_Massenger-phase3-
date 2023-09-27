package logic;

import com.google.gson.Gson;
import database.Database;
import ir.sharif.ap.phase3.model.help.ScheduledMessage;
import ir.sharif.ap.phase3.model.main.GroupChat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.Loop;

import java.io.File;
import java.time.LocalTime;
import java.util.Scanner;

public class ScheduleMassageController {

    private final File file = new File(Config.getConfig("saveNLoad").getProperty(String.class, "massageAddress"));
    private final Loop loop;
    private final Gson gson;

    public ScheduleMassageController() {
        loop = new Loop(0.016, this::checkMassages);
        gson = new Gson();
    }

    public void start() {
        loop.start();
    }

    private void checkMassages() {
        LocalTime currentTime = LocalTime.now();
        try {
            Scanner scanner;
            for (int i = 0; i < file.listFiles().length; i++) {
                File tmp = file.listFiles()[i];
                scanner = new Scanner(tmp);
                ScheduledMessage m = gson.fromJson(scanner.nextLine(), ScheduledMessage.class);
                LocalTime time = LocalTime.of(m.getHour(), m.getMinute());
                if (currentTime.isAfter(time)) {
                    GroupChat groupChat = Database.get(m.getGroupId(), GroupChat.class);
                    User user = Database.get(m.getSenderId(), User.class);
                    GroupController controller = new GroupController();
                    Message message = new Message(m.getTxt(), user);
                    Database.save(message);
                    controller.sendMassage(groupChat, message, user);
                    tmp.delete();
                    i--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}