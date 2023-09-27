package org.openjfx.view.lists.user.forward;

import ir.sharif.ap.phase3.event.tweet.ForwardEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import ir.sharif.ap.phase3.util.COMMANDS;

public class UserForListForward {

    private FXMLLoader loader;
    private HBox hBox;

    public UserForListForward(UserCopy forwardFrom, UserCopy forwardTo, String massage, EventListener listener) {

        Config config = Config.getConfig("lists");
        try {
            loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"userForwardAddress")));
            hBox = loader.load();
            UserForListForwardView view = loader.getController();
            view.getUsernameLabel().setText(forwardTo.getUsername());
            view.setListener(command -> {
                if(command == COMMANDS.FORWARD){
                    ForwardEvent event = new ForwardEvent(forwardTo.getId(), forwardFrom.getId(), massage);
                    listener.listen(event);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public HBox gethBox() {
        return hBox;
    }
}