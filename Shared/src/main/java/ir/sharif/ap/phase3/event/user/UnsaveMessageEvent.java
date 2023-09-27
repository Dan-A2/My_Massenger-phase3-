package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class UnsaveMessageEvent extends UserEvent{

    private final int userId;
    private final int messageID;

    public UnsaveMessageEvent(int userId, int messageID) {
        this.userId = userId;
        this.messageID = messageID;
    }

    public int getUserId() {
        return userId;
    }

    public int getMessageID() {
        return messageID;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitUnsaveMessage(this);
    }
}