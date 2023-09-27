package org.openjfx.view.yourAccount.youeAccount;

import ir.sharif.ap.phase3.event.tweet.PostTweetEvent;
import ir.sharif.ap.phase3.event.user.ShowAListEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import ir.sharif.ap.phase3.util.ListType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.openjfx.SceneManager;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.connector.EventListener;
import org.openjfx.view.lists.Lists;
import org.openjfx.view.mainMenu.MainMenu;
import org.openjfx.view.tweet.TweetPage;
import org.openjfx.view.yourAccount.profile.Profile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class YourAccount {

    private final EventListener listener;
    private AnchorPane pane;
    private File data;

    public YourAccount(EventListener listener) {
        this.listener = listener;
    }

    public void create(){
        UserCopy user = MainMenu.getCurrentUser();
        Config config = Config.getConfig("yourAccount");
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class, "yourAccountAddress")));
        try {
            pane = loader.load();
            YourAccountView currentView = loader.getController();
            currentView.setYourAccountListener(command -> {
                if(command == COMMANDS.POSTTWEET) {
                    postTweet(user, currentView);
                } else if (command == COMMANDS.SELECTIMAGE) {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(config.getProperty("imageType"), "*.png");
                    fileChooser.getExtensionFilters().addAll(extFilterPNG);
                    try {
                        data = fileChooser.showOpenDialog(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (command == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (command == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                } else if(command == COMMANDS.SHOWBLOCKED) {
                    listener.listen(new ShowAListEvent(ListType.Blocked, user.getId()));
                } else if(command == COMMANDS.SHOWFOLLOWERS) {
                    listener.listen(new ShowAListEvent(ListType.Followers, user.getId()));
                } else if(command == COMMANDS.SHOWFOLLOWINGS) {
                    listener.listen(new ShowAListEvent(ListType.Followings, user.getId()));
                } else if(command == COMMANDS.SHOWSORTINGS) {
                    listener.listen(new ShowAListEvent(ListType.Sorting, user.getId()));
                } else if(command == COMMANDS.SHOWMYTWEETS) {
                    listener.listen(new ShowAListEvent(ListType.ShowMyTweets, user.getId()));
                } else if(command == COMMANDS.GOTOPROFILE) {
                    Profile profile = new Profile();
                    profile.show(listener);
                } else if (command == COMMANDS.SHOWNOTIFS) {
                    listener.listen(new ShowAListEvent(ListType.Notification, user.getId()));
                } else if (command == COMMANDS.SHOWREQUESTERS) {
                    listener.listen(new ShowAListEvent(ListType.Requester, user.getId()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void postTweet(UserCopy user, YourAccountView currentView) {
        if(currentView.getTweetArea().getText().equals("")) {
            JOptionPane.showMessageDialog(null, Config.getConfig("yourAccount").getProperty(String.class,"yourAccountMassage2"));
            return;
        }
        currentView.getTweetArea().setVisible(false);
        currentView.getPostBtn().setVisible(false);
        currentView.getAttachBtn().setVisible(false);
        PostTweetEvent event = null;
        if (data != null) {
            try {
                ImageConvertor convertor = new ImageConvertor();
                event = new PostTweetEvent(currentView.getTweetArea().getText(), user.getId(), convertor.selectImage(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            event = new PostTweetEvent(currentView.getTweetArea().getText(), user.getId(), null);
        }
        currentView.getTweetArea().clear();
        listener.listen(event);
    }

    public AnchorPane getPane() {
        return pane;
    }
}