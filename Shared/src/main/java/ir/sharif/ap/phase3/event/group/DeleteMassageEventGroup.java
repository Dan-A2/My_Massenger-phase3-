package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.response.Response;

public class DeleteMassageEventGroup extends GroupEvent {

    private final int groupId;
    private final int massageId;

    public DeleteMassageEventGroup(int groupId, int massageId) {
        this.groupId = groupId;
        this.massageId = massageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getMassageId() {
        return massageId;
    }

    @Override
    public Response visit(GroupVisitor visitor) {
        return visitor.visitDeleteMessage(this);
    }
}