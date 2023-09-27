package ir.sharif.ap.phase3.model.main;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Tweet_Comment implements Savable{

    private int likes;
    private String text;
    private boolean isComment;
    private Integer motherId;
    @ManyToOne
    private User sender;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int reported;
    private Integer imageId;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tweet_Comment> comments;
    @ManyToMany(mappedBy = "likedTweets")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> likers;
    @ManyToMany(mappedBy = "savedTweets")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> savers;

    public Tweet_Comment(String text, User sender, boolean isComment) {
        this.likes = 0;
        this.reported = 0;
        this.text = text;
        this.comments = new LinkedList<>();
        this.isComment = isComment;
        this.sender = sender;
        this.comments = new LinkedList<>();
        this.likers = new LinkedList<>();
        this.savers = new LinkedList<>();
    }

    public Tweet_Comment() {

    }

    public String getText() {
        return text;
    }

    public List<Tweet_Comment> getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getSender() {
        return sender;
    }

    public int getID() {
        return ID;
    }

    public int getReported() {
        return reported;
    }

    public void setReported(int reported) {
        this.reported = reported;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setComments(List<Tweet_Comment> comments) {
        this.comments = comments;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }

    public List<User> getLikers() {
        return likers;
    }

    public List<User> getSavers() {
        return savers;
    }

    public Integer getMotherId() {
        return motherId;
    }

    public void setMotherId(int motherId) {
        this.motherId = motherId;
    }
}
