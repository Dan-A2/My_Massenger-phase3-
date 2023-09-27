package ir.sharif.ap.phase3.event.group;

import ir.sharif.ap.phase3.response.Response;

public class SendMassageToGroupEvent extends GroupEvent {

    private final int groupId;
    private final String txt;
    private final int senderId;
    private final byte[] image;

    public SendMassageToGroupEvent(int groupId, String txt, int senderId, byte[] image) {
        this.groupId = groupId;
        this.txt = txt;
        this.senderId = senderId;
        this.image = image;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getTxt() {
        return txt;
    }

    public int getSenderId() {
        return senderId;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public Response visit(GroupVisitor visitor) {
        return visitor.visitSendMessage(this);
    }
}