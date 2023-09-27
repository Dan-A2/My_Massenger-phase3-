package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.response.Response;

import java.util.List;

public class CreateGroupEvent extends GroupEvent {

    private final int userId; // creator
    private final List<Integer> usersId;
    private final String name;

    public CreateGroupEvent(int userId, List<Integer> usersId, String name) {
        this.userId = userId;
        this.usersId = usersId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public List<Integer> getUsersId() {
        return usersId;
    }

    public String getName() {
        return name;
    }

    @Override
    public Response visit(GroupVisitor visitor) {
        return visitor.visitCreateGroup(this);
    }
}