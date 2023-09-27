package ir.sharif.ap.phase3.event.entrance;

import ir.sharif.ap.phase3.event.user.UserEvent;
import ir.sharif.ap.phase3.event.user.UserVisitor;
import ir.sharif.ap.phase3.response.Response;

public class LogoutEvent extends EntranceEvent {

    private final int id;

    public LogoutEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public Response visit(EntranceVisitor visitor) {
        return visitor.visitLogout(this);
    }
}
