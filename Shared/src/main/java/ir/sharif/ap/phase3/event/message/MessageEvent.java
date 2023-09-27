package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class MessageEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getMessageVisitor());
    }

    public abstract Response visit(MessageVisitor visitor);
}