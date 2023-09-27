package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public class PostTweetEvent extends TweetEvent {

    private final String text;
    private final int senderID;
    private final byte[] image;

    public PostTweetEvent(String text, int senderId, byte[] image) {
        this.text = text;
        this.senderID = senderId;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public int getSenderID() {
        return senderID;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public Response visit(TweetVisitor visitor) {
        return visitor.visitPost(this);
    }
}
