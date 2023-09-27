package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.response.Response;

public abstract class TweetEvent extends Event {
    @Override
    public Response visit(EventVisitor visitor) {
        return visit(visitor.getTweetVisitor());
    }

    public abstract Response visit(TweetVisitor visitor);
}