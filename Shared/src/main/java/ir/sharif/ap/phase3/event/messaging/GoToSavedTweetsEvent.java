package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public class GoToSavedTweetsEvent extends MessagingEvent {

    private final int id;

    public GoToSavedTweetsEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public Response visit(MessagingVisitor visitor) {
        return visitor.visitGoToSavedTweets(this);
    }
}