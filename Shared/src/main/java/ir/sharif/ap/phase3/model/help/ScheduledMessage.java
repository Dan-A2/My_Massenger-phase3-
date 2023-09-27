package ir.sharif.ap.phase3.model.help;

public class ScheduledMessage {

    private static int lastId = 0;
    private final int id;
    private final String txt;
    private final int hour;
    private final int minute;
    private final int senderId;
    private final int groupId;

    public ScheduledMessage(String txt, int hour, int minute, int senderId, int groupId) {
        lastId++;
        this.id = lastId;
        this.txt = txt;
        this.hour = hour;
        this.minute = minute;
        this.senderId = senderId;
        this.groupId = groupId;
    }

    public String getTxt() {
        return txt;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getId() {
        return id;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        ScheduledMessage.lastId = lastId;
    }
}