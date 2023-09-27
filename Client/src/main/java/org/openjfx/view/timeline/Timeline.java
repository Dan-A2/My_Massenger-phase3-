package org.openjfx.view.timeline;

import ir.sharif.ap.phase3.event.general.TimelineEvent;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.Status;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.view.mainMenu.MainMenu;

public class Timeline {

    private AnchorPane pane;

    public void generate(EventListener listener) {
        Config config = Config.getConfig("timeline");
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"address")));
        try {
            pane = loader.load();
            TimelineView view = loader.getController();
            view.setTimelineListener(command -> {
                if (command == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (command == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                } else if (command == COMMANDS.SHOWFOLLOWINGTWEET) {
                    listener.listen(new TimelineEvent(MainMenu.getCurrentUser().getId(), Status.TimelineFollowing));
                } else if (command == COMMANDS.SHOWLIKED) {
                    listener.listen(new TimelineEvent(MainMenu.getCurrentUser().getId(), Status.TimelineLiked));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getPane() {
        return pane;
    }
}