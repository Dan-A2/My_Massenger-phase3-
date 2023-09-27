package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.response.Response;

public interface GroupVisitor {
    Response visitAddMember(AddMembersToGroupEvent event);
    Response visitCreateGroup(CreateGroupEvent event);
    Response visitDeleteMessage(DeleteMassageEventGroup eventGroup);
    Response visitLeaveGroup(GroupLeaveEvent event);
    Response visitSendMessage(SendMassageToGroupEvent event);
    Response visitShowGroup(ShowGroupEvent event);
}