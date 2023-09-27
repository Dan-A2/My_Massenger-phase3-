package controller.visitors;

import database.Database;
import ir.sharif.ap.phase3.event.group.*;
import ir.sharif.ap.phase3.model.main.GroupChat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.response.NoResponse;
import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.response.ShowGroupResponse;
import logic.GroupController;
import logic.MassageController;

import java.util.LinkedList;
import java.util.List;

public class GroupHandler implements GroupVisitor {

    private static GroupHandler groupHandler;

    private GroupHandler() {}

    public static GroupHandler getInstance() {
        if (groupHandler == null)
            groupHandler = new GroupHandler();
        return groupHandler;
    }

    @Override
    public Response visitAddMember(AddMembersToGroupEvent addMembersToGroupEvent) {
        GroupChat groupChat = Database.get(addMembersToGroupEvent.getGroupId(), GroupChat.class);
        GroupController controller = new GroupController();
        List<User> users = new LinkedList<>();
        for (Integer id : addMembersToGroupEvent.getUsersId()) {
            User user = Database.get(id, User.class);
            users.add(user);
        }
        controller.addMembers(groupChat, users);
        return new NoResponse();
    }

    @Override
    public Response visitCreateGroup(CreateGroupEvent createGroupEvent) {
        GroupController controller = new GroupController();
        User user = Database.get(createGroupEvent.getUserId(), User.class);
        List<User> users = new LinkedList<>();
        for (Integer id : createGroupEvent.getUsersId()) {
            users.add(Database.get(id, User.class));
        }
        controller.createGroup(user, users, createGroupEvent.getName());
        return new NoResponse();
    }

    @Override
    public Response visitDeleteMessage(DeleteMassageEventGroup deleteMassageEventGroup) {
        GroupChat groupChat = Database.get(deleteMassageEventGroup.getGroupId(), GroupChat.class);
        Message message = Database.get(deleteMassageEventGroup.getMassageId(), Message.class);
        MassageController controller = new MassageController();
        controller.deleteMassageForGroup(message, groupChat);
        return new NoResponse();
    }

    @Override
    public Response visitLeaveGroup(GroupLeaveEvent groupLeaveEvent) {
        GroupChat groupChat = Database.get(groupLeaveEvent.getGroupId(), GroupChat.class);
        User user = Database.get(groupLeaveEvent.getUserId(), User.class);
        GroupController controller = new GroupController();
        controller.leavingUser(user, groupChat, true);
        return new NoResponse();
    }

    @Override
    public Response visitSendMessage(SendMassageToGroupEvent sendMassageToGroupEvent) {
        GroupChat groupChat = Database.get(sendMassageToGroupEvent.getGroupId(), GroupChat.class);
        User user = Database.get(sendMassageToGroupEvent.getSenderId(), User.class);
        GroupController controller = new GroupController();
        Message message = new Message(sendMassageToGroupEvent.getTxt(), user);
        Database.save(message);
        controller.sendMassage(groupChat, message, user);
        return new NoResponse();
    }

    @Override
    public Response visitShowGroup(ShowGroupEvent showGroupEvent) {
        User user = Database.get(showGroupEvent.getUserId(), User.class);
        GroupChat groupChat = Database.get(showGroupEvent.getFiller().getId(), GroupChat.class);
        GroupController controller = new GroupController();
        controller.resetMassages(user, groupChat);
        return new ShowGroupResponse(showGroupEvent.getFiller(), false);
    }
}