package logic;

import database.Database;
import ir.sharif.ap.phase3.model.main.Notification;
import ir.sharif.ap.phase3.model.main.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificationHandler {

    static private final Logger logger = (Logger) LogManager.getLogger(NotificationHandler.class);

    public void sendNotif(String text, User receiver, User causer) {
        boolean isMuted = false;
        if (causer != null) {
            for (User u : receiver.getMuted()) {
                if (u.getId() == causer.getId()) {
                    isMuted = true;
                    break;
                }
            }
        }
        if (!isMuted) {
            Notification notification = new Notification(text);
            receiver.getMyNotifs().add(notification);
            Database.save(notification);
            Database.update(receiver);
            logger.info("a notification is sent to User " + receiver.getId());
        }
        logger.info("a notification is not sent to User " + receiver.getId() + " because of security issues!");
    }

}