package ir.sharif.ap.phase3.event.entrance;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class EntranceEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getEntranceVisitor());
    }

    public abstract Response visit(EntranceVisitor visitor);
}