package ir.sharif.ap.phase3.model.help;

import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.util.MessageStatus;

public class MassageFiller {

    private final int ID;
    private final String massage;
    private final String dateTime;
    private final boolean isForwarded;
    private String image;
    private final UserCopy sender;
    private final MessageStatus status;

    public MassageFiller(Message massage) {
        this.ID = massage.getID();
        this.massage = massage.getText();
        this.dateTime = massage.getDateTime();
        this.isForwarded = massage.isForwarded();
        this.sender = new UserCopy(massage.getSender());
        this.status = massage.getStatus();
    }

    public int getID() {
        return ID;
    }

    public String getMassage() {
        return massage;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isForwarded() {
        return isForwarded;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserCopy getSender() {
        return sender;
    }

    public MessageStatus getStatus() {
        return status;
    }
}