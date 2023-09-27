package logic;

import database.Database;
import ir.sharif.ap.phase3.model.main.Chat;
import ir.sharif.ap.phase3.model.main.GroupChat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MassageController {

    static private final Logger logger = (Logger) LogManager.getLogger(MassageController.class);

    public void deleteMassageForChat(Message massage, Chat chat) {
        for (Message m : massage.getSender().getSavedMassages()) {
            if (m.getID() == massage.getID()) {
                massage.getSender().getSavedMassages().remove(m);
                break;
            }
        }
        ChatController controller = new ChatController();
        User otherUser = controller.findTheOtherUser(chat, massage.getSender());
        for (Message m : otherUser.getSavedMassages()) {
            if (m.getID() == massage.getID()) {
                otherUser.getSavedMassages().remove(m);
                break;
            }
        }
        Database.update(massage.getSender());
        Database.update(otherUser);
        Database.update(chat);
        Database.delete(massage);
        logger.info("massage with ID " + massage.getID() + " is deleted for Chat " + chat.getID());
    }

    public void deleteMassageForGroup(Message massage, GroupChat chat) {
        for (User user : chat.getUsers()) {
            for (Message m : user.getSavedMassages()) {
                if (m.getID() == massage.getID()) {
                    user.getSavedMassages().remove(m);
                    Database.update(user);
                    break;
                }
            }
        }
        for (Message m : chat.getMessages()) {
            if (m.getID() == massage.getID()) {
                chat.getMessages().remove(m);
                break;
            }
        }
        Database.update(chat);
        Database.delete(massage);
        logger.info("massage with ID " + massage.getID() + " is deleted for Group " + chat.getId());
    }

    public void editMassage(Message massage, String text) {
        massage.setText(text);
        Database.update(massage);
        logger.info("Message " + massage.getID() + " is edited!");
    }
}