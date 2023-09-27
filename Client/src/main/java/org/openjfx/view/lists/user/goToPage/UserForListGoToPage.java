package org.openjfx.view.lists.user.goToPage;

import ir.sharif.ap.phase3.event.user.WatchUserPageEvent;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.openjfx.SceneManager;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.connector.EventListener;

public class UserForListGoToPage {

    private HBox box;

    public void createUser(String showTo, String showFrom, EventListener listener){
        Config config = Config.getConfig("lists");
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"userGoToAddress")));
            box = loader.load();
            UserForListGoToPageView componentController = loader.getController();
            componentController.getUsernameLabel().setText(showFrom);
            componentController.setListener(command -> {
                if(command == COMMANDS.GOTOPAGE){
                    WatchUserPageEvent event = new WatchUserPageEvent(showTo, showFrom);
                    listener.listen(event);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HBox getBox() {
        return box;
    }
}
