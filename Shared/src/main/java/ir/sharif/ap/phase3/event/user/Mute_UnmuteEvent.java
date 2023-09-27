package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class Mute_UnmuteEvent extends UserEvent {

    private final int doerId;
    private final int user2Id;
    private final boolean isMute;

    public Mute_UnmuteEvent(int doerId, int user2Id, boolean isMute) {
        this.doerId = doerId;
        this.user2Id = user2Id;
        this.isMute = isMute;
    }

    public int getDoerId() {
        return doerId;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public boolean isMute() {
        return isMute;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitMuteOrUnmute(this);
    }
}