package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public class SendMessageToSortingEvent extends MessagingEvent {

    private final String massage;
    private final int id;

    public String getMassage() {
        return massage;
    }

    public int getId() {
        return id;
    }

    public SendMessageToSortingEvent(String massage, int id) {
        this.massage = massage;
        this.id = id;
    }

    @Override
    public Response visit(MessagingVisitor visitor) {
        return visitor.visitSendMessageToSorting(this);
    }
}