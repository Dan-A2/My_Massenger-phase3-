package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.util.ListType;

public class ShowAListEvent extends UserEvent{

    private final ListType type;
    private final int userId;

    public ShowAListEvent(ListType type, int userId) {
        this.type = type;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public ListType getType() {
        return type;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitShowList(this);
    }
}