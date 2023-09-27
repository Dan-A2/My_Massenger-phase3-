package org.openjfx.view.lists.user.requester;

import ir.sharif.ap.phase3.event.user.DoRequestEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.listeners.CommandListener;

public class UserForListRequest {

    private AnchorPane pane;
    private CommandListener listener;

    public void create(UserCopy user1, UserCopy requester, EventListener listener) {

        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("lists").getProperty(String.class, "userRequestAddress")));
            pane = loader.load();
            UserForListRequestView view = loader.getController();
            view.getUsernameLabel().setText(requester.getUsername());
            view.setListener(command -> {
                DoRequestEvent event = new DoRequestEvent(user1.getId(), requester.getId(), command);
                listener.listen(event);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public AnchorPane getPane() {
        return pane;
    }

    public void setListener(CommandListener listener) {
        this.listener = listener;
    }
}