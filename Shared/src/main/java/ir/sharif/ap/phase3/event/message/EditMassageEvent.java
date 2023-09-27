package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.response.Response;

public class EditMassageEvent extends MessageEvent {

    private final int massageId;
    private final String text;

    public EditMassageEvent(int massageId, String text) {
        this.massageId = massageId;
        this.text = text;
    }

    public int getMassageId() {
        return massageId;
    }

    public String getText() {
        return text;
    }

    @Override
    public Response visit(MessageVisitor visitor) {
        return visitor.visitEditMessage(this);
    }
}