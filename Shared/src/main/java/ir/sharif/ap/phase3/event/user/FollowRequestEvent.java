package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class FollowRequestEvent extends UserEvent {

    private final int userId;
    private final int requesterId;
    private final boolean isAdd;

    public FollowRequestEvent(int userId, int requesterId, boolean b) {
        this.userId = userId;
        this.requesterId = requesterId;
        isAdd = b;
    }

    public int getUserId() {
        return userId;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public boolean isAdd() {
        return isAdd;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitFollowOrUnfollowRequest(this);
    }
}