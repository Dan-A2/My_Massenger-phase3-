package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public class ReportEvent extends TweetEvent {

    private final int TweetId;

    public ReportEvent(int tweetId) {
        TweetId = tweetId;
    }

    public int getTweetId() {
        return TweetId;
    }

    @Override
    public Response visit(TweetVisitor visitor) {
        return visitor.visitReport(this);
    }
}