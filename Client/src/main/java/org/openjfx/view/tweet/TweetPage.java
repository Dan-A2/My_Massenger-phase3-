package org.openjfx.view.tweet;

import ir.sharif.ap.phase3.event.user.DeleteNoteEvent;
import ir.sharif.ap.phase3.event.user.UnsaveMessageEvent;
import ir.sharif.ap.phase3.model.help.MassageFiller;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.connector.EventListener;
import org.openjfx.view.chat.massage.MassageFX;
import org.openjfx.view.chat.massage.withUserPic.MassageWithUserPic;
import org.openjfx.view.mainMenu.MainMenu;

import java.util.List;

public class TweetPage {

    private final Config config = Config.getConfig("tweet");
    private final EventListener listener;
    private Scene scene;

    public TweetPage(EventListener listener) {
        this.listener = listener;
    }

    public void showMyTweets(List<TweetFiller> tweets) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"tweetsAddress")));
            Parent root = loader.load();
            scene = new Scene(root);
            TweetPageView currentView = loader.getController();
            setListener(currentView);
            for (TweetFiller tweet : tweets) {
                if(!tweet.isComment()) {
                    TweetComponent tweetComponent = new TweetComponent();
                    tweetComponent.generate(tweet, listener);
                    currentView.getGridPane().add(tweetComponent.getPane(), 0, currentView.getGridPane().getRowCount()+1);
                    currentView.getGridPane().setMargin(tweetComponent.getPane(), new Insets(10));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showInGeneral(List<TweetFiller> tweets){
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"tweetsAddress")));
            Parent root = loader.load();
            scene = new Scene(root);
            TweetPageView currentView = loader.getController();
            setListener(currentView);
            for (TweetFiller tweet_comment : tweets) {
                TweetComponent tweetComponent = new TweetComponent();
                tweetComponent.generate(tweet_comment, listener);
                currentView.getGridPane().add(tweetComponent.getPane(), 0, currentView.getGridPane().getRowCount()+1);
                currentView.getGridPane().setMargin(tweetComponent.getPane(), new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSavedNotes(List<MassageFiller> fillers){
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"tweetsAddress")));
            Parent root = loader.load();
            scene = new Scene(root);
            TweetPageView currentView = loader.getController();
            setListener(currentView);
            for (MassageFiller filler : fillers) {
                MassageFX massageFX = new MassageFX(filler.getMassage());
                massageFX.setListener(commands -> {
                    if (commands == COMMANDS.DELETE) {
                        DeleteNoteEvent event = new DeleteNoteEvent(MainMenu.getCurrentUser().getId(), filler.getID());
                        SceneManager.getSceneManager().pop();
                        listener.listen(event);
                        fillers.remove(filler);
                        showSavedNotes(fillers);
                    }
                });
                currentView.getGridPane().add(massageFX.getMassagePane(), 0, currentView.getGridPane().getRowCount()+1);
                currentView.getGridPane().setMargin(massageFX.getMassagePane(), new Insets(10));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showSavedMassages(List<MassageFiller> fillers){
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"tweetsAddress")));
            Parent root = loader.load();
            scene = new Scene(root);
            TweetPageView currentView = loader.getController();
            setListener(currentView);
            for (MassageFiller filler : fillers) {
                MassageWithUserPic massage1 = new MassageWithUserPic(filler);
                massage1.create(Config.getConfig("chat").getProperty(String.class, "Deletable"));
                massage1.getView().setListener(commands -> {
                    if (commands == COMMANDS.DELETE) {
                        UnsaveMessageEvent event = new UnsaveMessageEvent(filler.getSender().getId(), filler.getID());
                        listener.listen(event);
                        fillers.remove(filler);
                        showSavedMassages(fillers);
                    }
                });
                massage1.getView().getSaveBtn().setVisible(false);
                massage1.getView().getEditBtn().setVisible(false);
                currentView.getGridPane().add(massage1.getPane(), 0, currentView.getGridPane().getRowCount()+1);
                currentView.getGridPane().setMargin(massage1.getPane(), new Insets(10));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setListener(TweetPageView view) {
        view.setListener(command -> {
            if(command == COMMANDS.BACK){
                SceneManager.getSceneManager().getBack();
            } else if (command == COMMANDS.MAINMENU) {
                SceneManager.getSceneManager().backToMain();
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}