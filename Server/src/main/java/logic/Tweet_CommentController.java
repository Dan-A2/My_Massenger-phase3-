package logic;

import database.Database;
import database.image.ImageSaver;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.model.main.Tweet_Comment;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.Checker;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import javafx.scene.image.Image;
import util.TweetFillerCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class Tweet_CommentController {

    static private final Logger logger = (Logger) LogManager.getLogger(Tweet_CommentController.class);

    public void like(Tweet_Comment tweet){ // notification & save handled in user controller
        tweet.setLikes(tweet.getLikes()+1);
        logger.info("Tweet " + tweet.getID() + " is liked");
    }

    public void dislike(Tweet_Comment tweet){ // notification and save handled in user controller
        tweet.setLikes(tweet.getLikes()-1);
        if(tweet.getLikes() < 0){
            tweet.setLikes(0);
        }
        logger.info("Tweet " + tweet.getID() + " is disliked");
    }

    public void report(Tweet_Comment tweet){
        tweet.setReported(tweet.getReported()+1);
        logger.info("Tweet " + tweet.getID() + " is reported");
        if (tweet.getReported() > 10) {
            logger.info("Tweet " + tweet.getID() + " is deleted because of reports");
            deleteTweet(tweet);
        } else {
            Database.update(tweet);
        }
    }

    public void deleteTweet(Tweet_Comment tweet) {
        if (tweet.getMotherId() != null) {
            Tweet_Comment mother = Database.get(tweet.getMotherId(), Tweet_Comment.class);
            for (Tweet_Comment comment : mother.getComments()) {
                if (comment.getID() == tweet.getID()) {
                    mother.getComments().remove(comment);
                    break;
                }
            }
            Database.update(mother);
        }
        for (int i = 0; i < tweet.getComments().size(); i++) {
            deleteTweet(tweet.getComments().get(i));
        }
        for (User u : tweet.getLikers()) {
            for (Tweet_Comment t : u.getLikedTweets()) {
                if (t.getID() == tweet.getID()) {
                    u.getLikedTweets().remove(t);
                    Database.update(u);
                    break;
                }
            }
        }
        for (User u : tweet.getSavers()) {
            for (Tweet_Comment t : u.getSavedTweets()) {
                if (t.getID() == tweet.getID()) {
                    u.getSavedTweets().remove(t);
                    Database.update(u);
                    break;
                }
            }
        }
        Database.delete(tweet);
        logger.info("Tweet " + tweet.getID() + " is deleted");
    }

    public void sortByLike(List<TweetFiller> tweets){
        for (int i = 0; i < tweets.size()-1; i++) {
            for (int j = i+1; j < tweets.size(); j++) {
                if(tweets.get(i).getLikes() < tweets.get(j).getLikes()){
                    TweetFiller arts = tweets.get(i);
                    tweets.set(i, tweets.get(j));
                    tweets.set(j, arts);
                    i = -1;
                    break;
                }
            }
        }
    }

    public List<TweetFiller> createTweetsToBeShown(User user) {
        List<TweetFiller> tweetsToBeShown = new LinkedList<>();
        List<Tweet_Comment> tweet_comments = Database.getAllTweets();
        for (Tweet_Comment t : tweet_comments) {
            if (t.getSender().isActive() && t.getSender().getId() != user.getId() && !Checker.getChecker().checkContainFollowing(user, t.getSender()) && t.getSender().isAccountPublic()) {
                tweetsToBeShown.add(TweetFillerCreator.createTweet(t, user));
            }
        }
        sortByLike(tweetsToBeShown);
        logger.info("User " + user.getUsername() + " has his tweetList created");
        return tweetsToBeShown;
    }

    public void addComment(String txt, User sender, Tweet_Comment tweet) {
        if (!txt.equals("")) {
            Tweet_Comment comment = new Tweet_Comment(txt, sender, true);
            NotificationHandler handler = new NotificationHandler();
            handler.sendNotif("User " + sender.getUsername() + " " + Config.getConfig("mainmenu").getProperty(String.class, "addCommentNotif"), tweet.getSender(), sender);
            logger.info(sender.getUsername() + " has added a comment to this tweet: " + tweet.getID());
            tweet.getComments().add(comment);
            comment.setMotherId(tweet.getID());
            Database.save(comment);
            Database.update(tweet);
        }
    }

    public void postTweet(String txt, User sender, byte[] image){
        System.out.println("posting tweet : " + txt);
        if(!txt.equals("")){
            Tweet_Comment tweet = new Tweet_Comment(txt, sender, false);
            if (image != null) {
                System.out.println("convert image started");
                ImageConvertor convertor = new ImageConvertor();
                ImageSaver saver = new ImageSaver();
                Image tmpImage = convertor.convertData(image);
                System.out.println("image is converted");
                int imageId = saver.saveImage(tmpImage);
                System.out.println("image saved to database");
                tweet.setImageId(imageId);
                System.out.println("tweet image is set");
            }
            logger.info("User " + sender.getId() + " has posted a tweet");
            sender.getMyTweets().add(tweet);
            Database.save(tweet);
            Database.update(sender);
            System.out.println("tweet posted");
        }
    }

}