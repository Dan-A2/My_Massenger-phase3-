package ir.sharif.ap.phase3.event.chat;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class ChatEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getChatVisitor());
    }

    public abstract Response visit(ChatVisitor visitor);
}
