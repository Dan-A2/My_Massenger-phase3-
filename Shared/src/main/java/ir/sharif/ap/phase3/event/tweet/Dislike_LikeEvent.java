package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public class Dislike_LikeEvent extends TweetEvent {

    private final int userId;
    private final int tweetId;
    private final boolean isLike;

    public Dislike_LikeEvent(int userId, int tweetId, boolean isLike) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.isLike = isLike;
    }

    public boolean isLike() {
        return isLike;
    }

    public int getUserId() {
        return userId;
    }

    public int getTweetId() {
        return tweetId;
    }

    @Override
    public Response visit(TweetVisitor visitor) {
        return visitor.visitLikeORDislike(this);
    }
}