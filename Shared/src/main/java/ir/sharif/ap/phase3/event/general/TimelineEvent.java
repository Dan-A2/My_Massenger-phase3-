package ir.sharif.ap.phase3.event.general;

import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.util.Status;

public class TimelineEvent extends GeneralEvent{

    private final int userId;
    private final Status status;

    public TimelineEvent(int userId, Status status) {
        this.userId = userId;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public Response visit(GeneralVisitor visitor) {
        return visitor.visitShowTimeline(this);
    }
}