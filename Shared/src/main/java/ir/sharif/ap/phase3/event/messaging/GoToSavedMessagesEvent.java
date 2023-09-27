package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public class GoToSavedMessagesEvent extends MessagingEvent {

    private final int id;

    public GoToSavedMessagesEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public Response visit(MessagingVisitor visitor) {
        return visitor.visitGoToSavedMessages(this);
    }
}