package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class Follow_UnfollowEvent extends UserEvent {

    private final int user1Id;
    private final int user2Id;
    private final boolean isFollow;

    public Follow_UnfollowEvent(int user1Id, int user2Id, boolean isFollow) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.isFollow = isFollow;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public int getUser2Id() {
        return user2Id;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitFollowOrUnfollow(this);
    }
}