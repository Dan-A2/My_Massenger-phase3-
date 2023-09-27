package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.response.Response;

public class SaveNoteEvent extends MessageEvent {

    private final int userID;
    private final String text;

    public SaveNoteEvent(int userID, String text) {
        this.userID = userID;
        this.text = text;
    }

    public int getUserID() {
        return userID;
    }

    public String getText() {
        return text;
    }

    @Override
    public Response visit(MessageVisitor visitor) {
        return visitor.visitSaveNote(this);
    }
}