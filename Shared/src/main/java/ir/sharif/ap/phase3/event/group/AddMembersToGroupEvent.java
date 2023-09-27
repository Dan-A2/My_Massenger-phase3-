package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.response.Response;

import java.util.List;

public class AddMembersToGroupEvent extends GroupEvent {

    private final int groupId;
    private final List<Integer> usersId;

    public AddMembersToGroupEvent(int groupId, List<Integer> usersId) {
        this.groupId = groupId;
        this.usersId = usersId;
    }

    public int getGroupId() {
        return groupId;
    }

    public List<Integer> getUsersId() {
        return usersId;
    }

    @Override
    public Response visit(GroupVisitor visitor) {
        return visitor.visitAddMember(this);
    }
}