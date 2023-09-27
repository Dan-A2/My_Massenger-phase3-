package org.openjfx.view.tweet.comment;

import ir.sharif.ap.phase3.event.tweet.CommentEvent;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.connector.EventListener;

public class AddComment {

    private EventListener listener;

    public void show(TweetFiller tweet, EventListener listener){
        Config config = Config.getConfig("tweet");
        this.listener = listener;
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class, "addCommentAddress")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            AddCommentView thisView = loader.getController();
            thisView.getTweetLabel().setText(tweet.getText());
            thisView.getUsernameLabel().setText(tweet.getSenderUsername());
            thisView.setAddCommentListener(command -> {
                if(command == COMMANDS.ADDCOMMENT){
                    createComment(thisView.getCommentArea().getText(), tweet.getSenderID(), tweet.getID());
                } else if (command == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (command == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                }
            });
            SceneManager.getSceneManager().push(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createComment(String comment, int senderId, int motherId){

        CommentEvent event = new CommentEvent(comment, senderId, motherId);
        listener.listen(event);

    }

}
