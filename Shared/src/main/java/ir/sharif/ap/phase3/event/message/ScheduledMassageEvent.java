package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.response.Response;

public class ScheduledMassageEvent extends MessageEvent{

    private final String txt;
    private final int senderId;
    private final int groupId;
    private final int hour;
    private final int minute;

    public ScheduledMassageEvent(String txt, int senderId, int groupId, int hour, int minute) {
        this.txt = txt;
        this.senderId = senderId;
        this.groupId = groupId;
        this.hour = hour;
        this.minute = minute;
    }

    public String getTxt() {
        return txt;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public Response visit(MessageVisitor visitor) {
        return visitor.visitScheduled(this);
    }
}