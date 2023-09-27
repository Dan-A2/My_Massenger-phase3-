package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public class ForwardEvent extends TweetEvent {

    private final int forwardToId;
    private final int forwardFromId;
    private final String tweet;

    public ForwardEvent(int forwardToId, int forwardFromId, String tweet) {
        this.forwardToId = forwardToId;
        this.forwardFromId = forwardFromId;
        this.tweet = tweet;
    }

    public String getTweetText() {
        return tweet;
    }

    public int getForwardToId() {
        return forwardToId;
    }

    public int getForwardFromId() {
        return forwardFromId;
    }

    @Override
    public Response visit(TweetVisitor visitor) {
        return visitor.visitForward(this);
    }
}