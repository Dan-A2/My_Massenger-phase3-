package util;

import database.image.ImageSaver;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.model.main.Tweet_Comment;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.ImageConvertor;

public class TweetFillerCreator {

    public static TweetFiller createTweet(Tweet_Comment tweet_comment, User showTo) {
        TweetFiller tweetFiller = new TweetFiller(tweet_comment, showTo);
        if (tweet_comment.getImageId() != null && tweet_comment.getImageId() > 0) {
            ImageConvertor convertor = new ImageConvertor();
            ImageSaver saver = new ImageSaver();
            tweetFiller.setImage(convertor.convertByte(convertor.selectImage(saver.getImage(tweet_comment.getImageId()))));
        }
        if (tweet_comment.getSender().getProfileImageId() != null && tweet_comment.getSender().getProfileImageId() > 0) {
            ImageConvertor convertor = new ImageConvertor();
            ImageSaver saver = new ImageSaver();
            tweetFiller.setSenderProfileImage(convertor.convertByte(convertor.selectImage(saver.getImage(tweet_comment.getSender().getProfileImageId()))));
        }
        return tweetFiller;
    }

}