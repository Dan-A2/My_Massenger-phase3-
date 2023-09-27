package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public class CommentEvent extends TweetEvent {

    private final String txt;
    private final int senderId;
    private final int tweetId;

    public CommentEvent(String txt, int senderId, int tweetId) {
        this.txt = txt;
        this.senderId = senderId;
        this.tweetId = tweetId;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public String getTxt() {
        return txt;
    }

    @Override
    public Response visit(TweetVisitor visitor) {
        return visitor.visitAddComment(this);
    }
}