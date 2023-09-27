package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class DeleteUserEvent extends UserEvent {

    private final int userId;

    public DeleteUserEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitDeleteAcc(this);
    }
}