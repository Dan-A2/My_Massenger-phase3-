package ir.sharif.ap.phase3.event.general;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class GeneralEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getGeneralVisitor());
    }

    public abstract Response visit(GeneralVisitor visitor);
}