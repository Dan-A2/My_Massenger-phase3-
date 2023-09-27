package logic;

import database.Database;
import ir.sharif.ap.phase3.model.main.GroupChat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.MessageStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GroupController {

    static private final Logger logger = (Logger) LogManager.getLogger(GroupController.class);

    private final UserController controller = new UserController();

    public void sendMassage(GroupChat groupChat, Message massage, User sender) {
        massage.setStatus(MessageStatus.Group);
        Database.update(massage);
        groupChat.getMessages().add(massage);
        addUnseenMassages(sender, groupChat);
        Database.update(groupChat);
        logger.info("Message " + massage.getID() + " is sent to Group " + groupChat.getId());
    }

    public void addUnseenMassages(User user, GroupChat groupChat) {
        NotificationHandler handler = new NotificationHandler();
        for (User user1 : groupChat.getUsers()) {
            if (user1.getId() != user.getId()) {
                handler.sendNotif(user.getUsername() + " " + Config.getConfig("chat").getProperty(String.class, "groupNewMassageNotif") + " " + groupChat.getGroupName(), user1, null);
                user1.getUnseenMassagesGroups().put(groupChat.getId(), user1.getUnseenMassagesGroups().get(groupChat.getId())+1);
                Database.update(user1);
            }
        }
    }

    public void leavingUser(User user, GroupChat groupChat, boolean announceOthers) {
        if (announceOthers) {
            Message massage = new Message(Config.getConfig("chat").getProperty(String.class, "leaveGroupMassage"), user);
            Database.save(massage);
            sendMassage(groupChat, massage, user);
        }
        for (User u : groupChat.getUsers()) {
            if (u.getId() == user.getId()) {
                groupChat.getUsers().remove(u);
                break;
            }
        }
        for (GroupChat g : user.getGroups()) {
            if (g.getId() == groupChat.getId()) {
                user.getGroups().remove(g);
                break;
            }
        }
        user.getUnseenMassagesGroups().remove(new Integer(groupChat.getId()));
        user.getLeftGroups().add(groupChat);
        Database.update(groupChat);
        Database.update(user);
        logger.info("User " + user.getId() + " has left the Group " + groupChat.getId());
    }

    public void createGroup(User user, List<User> users, String groupName) {
        GroupChat chat = new GroupChat(groupName);
        chat.setUsers(users);
        chat.getUsers().add(user);
        for (User user1 : chat.getUsers()) {
            controller.addUserToGroup(user1, chat);
        }
        Database.save(chat);
        logger.info("a new group with ID " + chat.getId() + " is created");
    }

    public void resetMassages(User user, GroupChat chat) {
        user.getUnseenMassagesGroups().remove(new Integer(chat.getId()));
        user.getUnseenMassagesGroups().put(chat.getId(), 0);
        Database.update(chat);
        Database.update(user);
        logger.info("the Group " + chat.getId() + " has reset messages for User " + user.getId());
    }

    public void addMembers(GroupChat chat, List<User> users) {
        for (User user : users) {
            chat.getUsers().add(user);
            controller.addUserToGroup(user, chat);
        }
        Database.update(chat);
        logger.info(users.size() + " new members are added to Group " + chat.getId());
    }
}