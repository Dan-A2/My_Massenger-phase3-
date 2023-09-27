package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public class OpenCreateSortingEvent extends MessagingEvent {

    private final int id;

    public OpenCreateSortingEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public Response visit(MessagingVisitor visitor) {
        return visitor.visitCreateSorting(this);
    }
}