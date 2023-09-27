package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public class ShowGroupsEvent extends MessagingEvent{

    private final int userId;

    public ShowGroupsEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Response visit(MessagingVisitor visitor) {
        return visitor.visitShowGroups(this);
    }
}