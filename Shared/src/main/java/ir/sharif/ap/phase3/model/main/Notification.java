package ir.sharif.ap.phase3.model.main;

import ir.sharif.ap.phase3.util.Config;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Notification implements Savable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String notif;
    private String dateTime;

    public Notification(String notif) {
        this.notif = notif;
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.dateTime = time.format(formatter);
    }

    public Notification() {
    }

    public String getNotif() {
        return notif;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}