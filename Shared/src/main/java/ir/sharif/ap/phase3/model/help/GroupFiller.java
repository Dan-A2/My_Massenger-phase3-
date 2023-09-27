package ir.sharif.ap.phase3.model.help;

import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.model.main.GroupChat;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GroupFiller {

    private final String groupName;
    private final int id;
    private final List<UserCopy> users = new LinkedList<>();
    private final List<MassageFiller> massages = new LinkedList<>();
    private final Map<Integer, Integer> unseenMessages = new HashMap<>();

    public GroupFiller(GroupChat groupChat) {
        this.groupName = groupChat.getGroupName();
        this.id = groupChat.getId();
        for (User u : groupChat.getUsers()) {
            users.add(new UserCopy(u));
            unseenMessages.put(u.getId(), u.getUnseenMassagesGroups().get(id));
        }
        for (Message m : groupChat.getMessages()) {
            massages.add(new MassageFiller(m));
        }

    }

    public String getGroupName() {
        return groupName;
    }

    public int getId() {
        return id;
    }

    public List<UserCopy> getUsers() {
        return users;
    }

    public List<MassageFiller> getMassages() {
        return massages;
    }

    public Map<Integer, Integer> getUnseenMessages() {
        return unseenMessages;
    }
}