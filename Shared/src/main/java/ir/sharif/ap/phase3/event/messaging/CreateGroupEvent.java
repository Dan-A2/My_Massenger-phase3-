package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public class CreateGroupEvent extends MessagingEvent{

    private final int userId;

    public CreateGroupEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Response visit(MessagingVisitor visitor) {
        return visitor.visitCreateGroup(this);
    }
}