package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class GroupEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getGroupVisitor());
    }

    public abstract Response visit(GroupVisitor visitor);
}