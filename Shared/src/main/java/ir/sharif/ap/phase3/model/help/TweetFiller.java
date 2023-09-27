package ir.sharif.ap.phase3.model.help;

import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.model.main.Tweet_Comment;
import ir.sharif.ap.phase3.util.Checker;

import java.util.LinkedList;

public class TweetFiller {

    private final int likes;
    private final String text;
    private final Integer senderID;
    private final String senderUsername;
    private final int ID;
    private final int reported;
    private final LinkedList<Integer> commentsId = new LinkedList<>();
    private final boolean isComment;
    private final boolean containLike, containSave, containBlacklist, containMuted;
    private String senderProfileImage;
    private String image;

    public TweetFiller(Tweet_Comment tweet_comment, User showTo) {
        this.likes = tweet_comment.getLikes();
        this.text = tweet_comment.getText();
        this.senderID = tweet_comment.getSender().getId();
        this.ID = tweet_comment.getID();
        this.reported = tweet_comment.getReported();
        for (Tweet_Comment comment : tweet_comment.getComments()) {
            commentsId.add(comment.getID());
        }
        this.senderUsername = tweet_comment.getSender().getUsername();
        this.containLike = Checker.getChecker().checkContainLike(showTo, tweet_comment);
        this.containSave = Checker.getChecker().checkContainSave(showTo, tweet_comment);
        this.containBlacklist = Checker.getChecker().checkContainBlacklist(showTo, tweet_comment.getSender());
        this.containMuted = Checker.getChecker().checkContainMuted(showTo, tweet_comment.getSender());
        this.isComment = tweet_comment.isComment();
    }

    public int getLikes() {
        return likes;
    }

    public String getText() {
        return text;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public int getID() {
        return ID;
    }

    public int getReported() {
        return reported;
    }

    public LinkedList<Integer> getCommentsId() {
        return commentsId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public boolean isContainLike() {
        return containLike;
    }

    public boolean isContainSave() {
        return containSave;
    }

    public boolean isContainBlacklist() {
        return containBlacklist;
    }

    public boolean isContainMuted() {
        return containMuted;
    }

    public boolean isComment() {
        return isComment;
    }

    public String getSenderProfileImage() {
        return senderProfileImage;
    }

    public void setSenderProfileImage(String senderProfileImage) {
        this.senderProfileImage = senderProfileImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}