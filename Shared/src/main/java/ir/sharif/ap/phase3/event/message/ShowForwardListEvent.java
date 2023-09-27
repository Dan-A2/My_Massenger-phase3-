package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.response.Response;

public class ShowForwardListEvent extends MessageEvent {

    private final int forwardFromId;
    private final int ans;
    private final String massageToBeForwarded;

    public ShowForwardListEvent(int forwardFromId, int ans, String massageToBeForwarded) {
        this.forwardFromId = forwardFromId;
        this.ans = ans;
        this.massageToBeForwarded = massageToBeForwarded;
    }

    public int getForwardFromId() {
        return forwardFromId;
    }

    public int getAns() {
        return ans;
    }

    public String getMassageToBeForwarded() {
        return massageToBeForwarded;
    }

    @Override
    public Response visit(MessageVisitor visitor) {
        return visitor.visitShowForwardList(this);
    }
}