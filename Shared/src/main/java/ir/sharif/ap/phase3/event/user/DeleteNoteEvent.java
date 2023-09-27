package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class DeleteNoteEvent extends UserEvent{

    private final int userId;
    private final int noteId;

    public DeleteNoteEvent(int userId, int noteId) {
        this.userId = userId;
        this.noteId = noteId;
    }

    public int getUserId() {
        return userId;
    }

    public int getNoteId() {
        return noteId;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitDeleteNote(this);
    }
}