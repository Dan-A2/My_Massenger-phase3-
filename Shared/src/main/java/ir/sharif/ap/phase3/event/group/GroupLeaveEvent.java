package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.response.Response;

public class GroupLeaveEvent extends GroupEvent {

    private final int userId;
    private final int groupId;

    public GroupLeaveEvent(int userId, int groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public int getGroupId() {
        return groupId;
    }

    @Override
    public Response visit(GroupVisitor visitor) {
        return visitor.visitLeaveGroup(this);
    }
}