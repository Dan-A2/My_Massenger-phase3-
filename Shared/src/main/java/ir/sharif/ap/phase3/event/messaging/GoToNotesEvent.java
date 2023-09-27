package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public class GoToNotesEvent extends MessagingEvent {

    private final int userId;

    public GoToNotesEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Response visit(MessagingVisitor visitor) {
        return visitor.visitGoToNotes(this);
    }
}