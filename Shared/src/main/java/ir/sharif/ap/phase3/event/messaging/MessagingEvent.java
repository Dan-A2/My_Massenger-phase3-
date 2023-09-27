package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class MessagingEvent extends Event {

    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getMessagingVisitor());
    }

    public abstract Response visit(MessagingVisitor visitor);
}