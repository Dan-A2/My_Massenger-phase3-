package logic;

import database.Database;
import ir.sharif.ap.phase3.model.main.Chat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.MessageStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatController {

    static private final Logger logger = (Logger) LogManager.getLogger(ChatController.class);

    public Chat findChat(User u1, User u2){
        if (Database.getChat(u1, u2) != null) {
            return Database.getChat(u1, u2);
        }
        Chat chat = new Chat(u1, u2);
        Database.save(chat);
        return chat;
    }

    public User findTheOtherUser(Chat chat, User user){
        if(chat.getUser1().getUsername().equals(user.getUsername())){
            return chat.getUser2();
        }
        return chat.getUser1();
    }

    public void resetMassages(Chat chat, User user) {
        if (chat.getUser1().getId() == user.getId()) {
            chat.setUser1UnseenMassages(0);
        } else {
            chat.setUser2UnseenMassages(0);
        }
        fixMassageStatus(chat);
        Database.update(chat);
        logger.info("the chat messages of Chat " + chat.getID() + " is reset fo User " + user.getId());
    }

    public void userIsOnline(User user) {
        for (Chat chat : user.getMyChats()) {
            for (int i = chat.getMessages().size()-1; i > -1 ; i--) {
                if (chat.getMessages().get(i).getStatus() == MessageStatus.Sent) {
                    chat.getMessages().get(i).setStatus(MessageStatus.Delivered);
                    Database.update(chat.getMessages().get(i));
                } else {
                    break;
                }
            }
        }
    }

    public void deleteChat(Chat chat) {
        for (int i = 0; i < chat.getMessages().size(); i++) {
            Message tmp = chat.getMessages().remove(i);
            for (int j = 0; j < chat.getUser1().getSavedMassages().size(); j++) {
                if (chat.getUser1().getSavedMassages().get(j).getID() == tmp.getID()) {
                    chat.getUser1().getSavedMassages().remove(j);
                    j--;
                }
            }
            Database.update(chat.getUser1());
            for (int j = 0; j < chat.getUser2().getSavedMassages().size(); j++) {
                if (chat.getUser2().getSavedMassages().get(j).getID() == tmp.getID()) {
                    chat.getUser2().getSavedMassages().remove(j);
                    j--;
                }
            }
            Database.update(chat.getUser2());
            Database.update(chat);
            Database.delete(tmp);
            i--;
        }
        for (Chat c : chat.getUser2().getMyChats()) {
            if (c.getID() == chat.getID()) {
                chat.getUser2().getMyChats().remove(c);
                break;
            }
        }
        for (Chat c : chat.getUser1().getMyChats()) {
            if (c.getID() == chat.getID()) {
                chat.getUser1().getMyChats().remove(c);
                break;
            }
        }
        Database.update(chat.getUser1());
        Database.update(chat.getUser2());
        Database.update(chat);
        Database.delete(chat);
        logger.info("Chat " + chat.getID() + " is deleted");
    }

    private void fixMassageStatus(Chat chat) {
        if (chat.getMessages().size() == 0) return;
        for (int i = chat.getMessages().size()-1; i > -1 ; i--) {
            if (chat.getMessages().get(i).getStatus() == MessageStatus.Sent || chat.getMessages().get(i).getStatus() == MessageStatus.Delivered) {
                chat.getMessages().get(i).setStatus(MessageStatus.Seen);
                Database.update(chat.getMessages().get(i));
            } else {
                break;
            }
        }
    }
}