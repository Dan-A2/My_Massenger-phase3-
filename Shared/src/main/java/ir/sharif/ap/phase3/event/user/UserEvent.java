package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class UserEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getUserVisitor());
    }

    public abstract Response visit(UserVisitor visitor);
}