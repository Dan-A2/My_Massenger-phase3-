package ir.sharif.ap.phase3.event;

import ir.sharif.ap.phase3.response.Response;

public abstract class Event {
    public abstract Response visit(EventVisitor visitor);
}