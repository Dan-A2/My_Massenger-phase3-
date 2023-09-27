package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.main.Notification;
import ir.sharif.ap.phase3.model.main.User;

import java.util.LinkedList;
import java.util.List;

public class NotificationsResponse extends Response{

    private final List<Notification> notifications;

    public NotificationsResponse(List<Notification> notifications) {
        this.notifications = new LinkedList<>(notifications);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowNotif(this);
    }
}