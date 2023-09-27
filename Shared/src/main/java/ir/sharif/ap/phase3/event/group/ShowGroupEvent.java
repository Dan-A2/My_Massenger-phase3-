package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.model.help.GroupFiller;
import ir.sharif.ap.phase3.response.Response;

public class ShowGroupEvent extends GroupEvent{

    private final int userId;
    private final GroupFiller filler;

    public ShowGroupEvent(int userId, GroupFiller filler) {
        this.userId = userId;
        this.filler = filler;
    }

    public int getUserId() {
        return userId;
    }

    public GroupFiller getFiller() {
        return filler;
    }

    @Override
    public Response visit(GroupVisitor visitor) {
        return visitor.visitShowGroup(this);
    }
}