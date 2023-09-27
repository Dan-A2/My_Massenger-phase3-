package ir.sharif.ap.phase3.event.sorting;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class SortingEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getSortingVisitor());
    }

    public abstract Response visit(SortingVisitor visitor);
}