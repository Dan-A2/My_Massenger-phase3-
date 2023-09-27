package ir.sharif.ap.phase3.model.main;

import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.MessageStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Message implements Savable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String text;
    private String dateTime;
    private boolean isForwarded;
    private Integer imageId;
    private MessageStatus status;
    @ManyToOne
    private User sender;
    public Message(String text, User sender){
        this.text = text;
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.dateTime = time.format(formatter);
        this.sender = sender;
    }

    public Message() {
    }

    public String getText() {
        return text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getID() {
        return ID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public boolean isForwarded() {
        return isForwarded;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setForwarded(boolean forwarded) {
        isForwarded = forwarded;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}