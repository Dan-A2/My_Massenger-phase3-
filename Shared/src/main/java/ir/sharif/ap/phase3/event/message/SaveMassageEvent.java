package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.response.Response;

public class SaveMassageEvent extends MessageEvent {

    private final int saverId;
    private final int massageId;

    public SaveMassageEvent(int saverId, int massageId) {
        this.saverId = saverId;
        this.massageId = massageId;
    }

    public int getSaverId() {
        return saverId;
    }

    public int getMassageId() {
        return massageId;
    }

    @Override
    public Response visit(MessageVisitor visitor) {
        return visitor.visitSaveMessage(this);
    }
}