package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public class ShowCommentsEvent extends TweetEvent {

    private final int showToId;
    private final int mainTweetId;

    public ShowCommentsEvent(int showToId, int mainTweetId) {
        this.showToId = showToId;
        this.mainTweetId = mainTweetId;
    }

    public int getMainTweetId() {
        return mainTweetId;
    }

    public int getShowToId() {
        return showToId;
    }

    @Override
    public Response visit(TweetVisitor visitor) {
        return visitor.visitShowComments(this);
    }
}