package ir.sharif.ap.phase3.model.help;

import java.util.LinkedList;
import java.util.List;

public class CommentPageFiller {

    private final TweetFiller motherTweet;
    private final List<TweetFiller> comments;

    public CommentPageFiller(TweetFiller motherTweet, List<TweetFiller> comments) {
        this.motherTweet = motherTweet;
        this.comments = new LinkedList<>(comments);
    }

    public TweetFiller getMotherTweet() {
        return motherTweet;
    }

    public List<TweetFiller> getComments() {
        return comments;
    }
}