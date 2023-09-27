package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class BlockEvent extends UserEvent {

    private final int user1Id;
    private final int user2Id;
    private final boolean isBlock;

    public BlockEvent(int user1Id, int user2Id, boolean isBlock) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.isBlock = isBlock;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public boolean isBlock() {
        return isBlock;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitBlock(this);
    }
}