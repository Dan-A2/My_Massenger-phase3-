package org.openjfx.view.tweet.comment;

import ir.sharif.ap.phase3.model.help.CommentPageFiller;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.ImagePattern;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.view.tweet.TweetComponent;

public class CommentsPage {

    private final CommentPageFiller filler;
    private final EventListener listener;
    private Scene scene;

    public CommentsPage(CommentPageFiller filler, EventListener listener) {
        this.filler = filler;
        this.listener = listener;
    }

    public void create(){
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("tweet").getProperty(String.class,"commentAddress")));
        try {
            Parent root = loader.load();
            scene = new Scene(root);
            CommentsPageView view = loader.getController();
            view.getTweetLabel().setText(filler.getMotherTweet().getText());
            view.getUsernameLabel().setText(filler.getMotherTweet().getSenderUsername());
            if (filler.getMotherTweet().getSenderProfileImage() != null) {
                ImageConvertor convertor = new ImageConvertor();
                view.getCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(filler.getMotherTweet().getSenderProfileImage()))));
            }
            for (TweetFiller comment : filler.getComments()) {
                TweetComponent comment2 = new TweetComponent();
                comment2.generate(comment, listener);
                view.getVBox().getChildren().add(comment2.getPane());
            }
            view.setListener(command -> {
                 if (command == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (command == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}