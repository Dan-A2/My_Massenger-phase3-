package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public class SaveTweetEvent extends TweetEvent {

    private final int saverId;
    private final int TweetId;
    private final boolean isSave;

    public SaveTweetEvent(int saverId, int tweetId, boolean isSave) {
        this.saverId = saverId;
        TweetId = tweetId;
        this.isSave = isSave;
    }

    public boolean isSave() {
        return isSave;
    }

    public int getSaverId() {
        return saverId;
    }

    public int getTweetId() {
        return TweetId;
    }

    @Override
    public Response visit(TweetVisitor visitor) {
        return visitor.visitSave(this);
    }
}
