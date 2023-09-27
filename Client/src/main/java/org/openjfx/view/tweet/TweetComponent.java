package org.openjfx.view.tweet;

import ir.sharif.ap.phase3.event.tweet.*;
import ir.sharif.ap.phase3.event.user.BlockEvent;
import ir.sharif.ap.phase3.event.user.Mute_UnmuteEvent;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.view.mainMenu.MainMenu;
import org.openjfx.view.tweet.comment.AddComment;
import org.openjfx.view.tweet.forward.Forward;

public class TweetComponent {

    private AnchorPane pane;
    private TweetFiller tweetCopy;
    private EventListener listener;

    public void generate(TweetFiller tweet, EventListener listener) {

        this.listener = listener;
        tweetCopy = tweet;
        FXMLLoader loader1 = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("tweet").getProperty(String.class,"tweetAddress")));
        FXMLLoader loader2 = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("tweet").getProperty(String.class,"tweetWithImageAddress")));
        try {
            if (tweet.getImage() != null) {
                pane = loader2.load();
            } else {
                pane = loader1.load();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageConvertor convertor = new ImageConvertor();
        if (tweet.getImage() != null) {
            TweetWithImageComponentView view = loader2.getController();
            view.getTweetLabel().setText(tweet.getText());
            view.getTweetImage().setImage(convertor.convertData(convertor.convertString(tweet.getImage())));
            setListener(view);
            view.getUsernameLabel().setText(tweet.getSenderUsername());
            if (tweetCopy.getSenderProfileImage() != null) {
                view.getCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(tweet.getSenderProfileImage()))));
            }
            update(view);
        } else {
            TweetComponentView view = loader1.getController();
            view.getTweetLabel().setText(tweet.getText());
            if (tweetCopy.getSenderProfileImage() != null) {
                view.getCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(tweet.getSenderProfileImage()))));
            }
            setListener(view);
            view.getUsernameLabel().setText(tweet.getSenderUsername());
            update(view);
        }

    }

    public AnchorPane getPane() {
        return pane;
    }

    private void setListener(TweetComponentView tweetComponentView){
        tweetComponentView.setListener(command -> {
            if(command == COMMANDS.ADDCOMMENT){
                AddComment addComment = new AddComment();
                addComment.show(tweetCopy, listener);
            } else if(command == COMMANDS.BLOCK){
                BlockEvent event = new BlockEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getSenderID(), true);
                listener.listen(event);
            } else if(command == COMMANDS.UNBLOCK){
                BlockEvent event = new BlockEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getSenderID(), false);
                listener.listen(event);
            } else if(command == COMMANDS.DISLIKE){
                Dislike_LikeEvent event = new Dislike_LikeEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getID(), false);
                listener.listen(event);
                System.out.println("dislike event sent");
            } else if(command == COMMANDS.LIKE){
                Dislike_LikeEvent event = new Dislike_LikeEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getID(), true);
                listener.listen(event);
                System.out.println("like event sent");
            } else if(command == COMMANDS.MUTE){
                Mute_UnmuteEvent event = new Mute_UnmuteEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getSenderID(), true);
                listener.listen(event);
            } else if(command == COMMANDS.UNMUTE){
                Mute_UnmuteEvent event = new Mute_UnmuteEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getSenderID(), false);
                listener.listen(event);
            } else if(command == COMMANDS.REPORT){
                ReportEvent event = new ReportEvent(tweetCopy.getID());
                listener.listen(event);
            } else if(command == COMMANDS.SAVE){
                SaveTweetEvent event = new SaveTweetEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getID(), true);
                listener.listen(event);
            } else if(command == COMMANDS.UNSAVE){
                SaveTweetEvent event = new SaveTweetEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getID(), false);
                listener.listen(event);
            } else if(command == COMMANDS.FORWARD){
                Forward forward = new Forward(tweetCopy.getSenderID(), tweetCopy.getText());
                forward.show(listener);
            } else if(command == COMMANDS.RESEND){
                if (MainMenu.getCurrentUser().getId() != tweetCopy.getSenderID()) {
                    ImageConvertor convertor = new ImageConvertor();
                    TweetEvent event = new PostTweetEvent(Config.getConfig("tweet").getProperty(String.class, "resend") + " " + tweetCopy.getText(), MainMenu.getCurrentUser().getId(), convertor.convertString(tweetCopy.getImage()));
                    listener.listen(event);
                }
            } else if (command == COMMANDS.SHOWCOMMENTS) {
                listener.listen(new ShowCommentsEvent(MainMenu.getCurrentUser().getId(), tweetCopy.getID()));
            }
        });
    }

    private <T extends TweetComponentView> void update(T t){
        t.getLikesLabel().setText(Integer.toString(tweetCopy.getLikes()));
        t.getCommentsLabel().setText(Integer.toString(tweetCopy.getCommentsId().size()));
        if(tweetCopy.isContainLike()){
            t.getLikeBtn().setVisible(false);
            t.getLikedBtn().setVisible(true);
        } else {
            t.getLikeBtn().setVisible(true);
            t.getLikedBtn().setVisible(false);
        }
        if(tweetCopy.isContainMuted()){
            t.getMuteBtn().setVisible(false);
            t.getMutedBtn().setVisible(true);
        } else {
            t.getMuteBtn().setVisible(true);
            t.getMutedBtn().setVisible(false);
        }
        if(tweetCopy.isContainBlacklist()){
            t.getBlockBtn().setVisible(false);
            t.getBlockedBtn().setVisible(true);
        } else {
            t.getBlockBtn().setVisible(true);
            t.getBlockedBtn().setVisible(false);
        }
        if (tweetCopy.isContainSave()) {
            t.getSaveBtn().setVisible(false);
            t.getUnsaveBtn().setVisible(true);
        } else {
            t.getSaveBtn().setVisible(true);
            t.getUnsaveBtn().setVisible(false);
        }
        if (tweetCopy.getSenderProfileImage() != null) {
            ImageConvertor convertor = new ImageConvertor();
            t.getCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(tweetCopy.getSenderProfileImage()))));
        }

    }

}