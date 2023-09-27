package ir.sharif.ap.phase3.model.help;

import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.Chat;

import java.util.LinkedList;
import java.util.List;

public class ChatFiller {

    private final UserCopy user1, user2;
    private final List<MassageFiller> massages = new LinkedList<>();
    private final int user1unseen, user2unseen;
    private final int chatId;

    public ChatFiller(Chat chat) {
        this.chatId = chat.getID();
        this.user1 = new UserCopy(chat.getUser1());
        this.user2 = new UserCopy(chat.getUser2());
        for (Message m : chat.getMessages()) {
            massages.add(new MassageFiller(m));
        }
        this.user1unseen = chat.getUser1UnseenMassages();
        this.user2unseen = chat.getUser2UnseenMassages();
    }

    public UserCopy getUser1() {
        return user1;
    }

    public UserCopy getUser2() {
        return user2;
    }

    public List<MassageFiller> getMassages() {
        return massages;
    }

    public int getUser1unseen() {
        return user1unseen;
    }

    public int getUser2unseen() {
        return user2unseen;
    }

    public int getChatId() {
        return chatId;
    }
}