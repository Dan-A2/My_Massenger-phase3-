package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.util.TweetPageType;

import java.util.List;

public class TweetPageResponse extends Response{

    private final List<TweetFiller> tweets;
    private final TweetPageType type;
    private final boolean isUpdate;

    public TweetPageResponse(List<TweetFiller> tweets, TweetPageType type, boolean isUpdate) {
        this.tweets = tweets;
        this.type = type;
        this.isUpdate = isUpdate;
    }

    public TweetPageType getType() {
        return type;
    }

    public List<TweetFiller> getTweets() {
        return tweets;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitTweetPage(this);
    }
}