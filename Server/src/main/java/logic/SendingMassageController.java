package logic;

import database.Database;
import database.image.ImageSaver;
import ir.sharif.ap.phase3.model.main.Chat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import ir.sharif.ap.phase3.util.MessageStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SendingMassageController {

    static private final Logger logger = (Logger) LogManager.getLogger(SendingMassageController.class);

    public void sendMassage1(User from, User to, String txt, boolean isForwarded, byte[] image) {
        Message massage = new Message(txt, from);
        massage.setForwarded(isForwarded);
        if (image != null) {
            ImageConvertor convertor = new ImageConvertor();
            ImageSaver saver = new ImageSaver();
            massage.setImageId(saver.saveImage(convertor.convertData(image)));
        }
        if (to.isOnline()) {
            massage.setStatus(MessageStatus.Delivered);
        } else {
            massage.setStatus(MessageStatus.Sent);
        }
        Database.save(massage);
        ChatController controller = new ChatController();
        Chat chat = controller.findChat(to, from);
        chat.getMessages().add(massage);
        if (chat.getUser1().getId() == from.getId()) {
            chat.setUser2UnseenMassages(chat.getUser2UnseenMassages() + 1);
        } else {
            chat.setUser1UnseenMassages(chat.getUser1UnseenMassages() + 1);
        }
        boolean hasChat1 = false;
        for (Chat c : to.getMyChats()) {
            if (c.getID() == chat.getID()) {
                hasChat1 = true;
                break;
            }
        }
        if (!hasChat1) {
            to.getMyChats().add(chat);
            Database.update(to);
        }
        for (Chat c : from.getMyChats()) {
            if (c.getID() == chat.getID()) {
                hasChat1 = true;
                break;
            }
        }
        if (!hasChat1) {
            from.getMyChats().add(chat);
            Database.update(from);
        }
        NotificationHandler handler = new NotificationHandler();
        Config config = Config.getConfig("massaging");
        handler.sendNotif(config.getProperty(String.class,"user") + " " + from.getUsername() + " " + config.getProperty(String.class, "sendMassageNotif"), to, from);
        Database.update(chat);
        logger.info("User " + from.getId() + " has sent a message to User " + to.getId());
    }

    public void sendMassageSorting(User from, String sortingName, String txt, boolean isForwarded){
        for (User to : from.getMySortings().get(sortingName).getUsers()) {
            sendMassage1(from, to, txt, isForwarded, null);
        }
    }

}