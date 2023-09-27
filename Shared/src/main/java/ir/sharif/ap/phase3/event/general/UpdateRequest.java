package ir.sharif.ap.phase3.event.general;

import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.util.Status;

public class UpdateRequest extends GeneralEvent {

    private final Status status;
    private final String oza; // for chat or group name

    public UpdateRequest(Status status, String oza) {
        this.status = status;
        this.oza = oza;
    }

    public Status getStatus() {
        return status;
    }

    public String getOza() {
        return oza;
    }

    @Override
    public Response visit(GeneralVisitor visitor) {
        return visitor.visitUpdate(this);
    }
}