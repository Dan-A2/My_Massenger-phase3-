package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.response.Response;

public class MassageEvent extends MessageEvent {

    private final int senderId;
    private final int receiverId;
    private final String massage;
    private final byte[] image;
    private final boolean isForwarded;

    public MassageEvent(int senderId, int receiverId, String massage, boolean isForwarded, byte[] image) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.massage = massage;
        this.image = image;
        this.isForwarded = isForwarded;
    }

    public String getMassage() {
        return massage;
    }

    public byte[] getImage() {
        return image;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public boolean isForwarded() {
        return isForwarded;
    }

    @Override
    public Response visit(MessageVisitor visitor) {
        return visitor.visitSendMessage(this);
    }
}