package org.openjfx.view.lists.sorting;

import ir.sharif.ap.phase3.event.sorting.ForwardToSortingEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.openjfx.SceneManager;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.connector.EventListener;

public class SortingForListForwardSend {

    private HBox hBox;

    public SortingForListForwardSend(UserCopy forwardFrom, String sortingName, String massage, boolean isForwarded , EventListener listener) {
        Config config = Config.getConfig("lists");
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"sortingForwardAddress")));
            hBox = loader.load();
            SortingForListForwardSendView view = loader.getController();
            view.getSortingNameLabel().setText(sortingName);
            if (isForwarded){
                view.getForwardSendBtn().setText(config.getProperty(String.class,"forward"));
            } else {
                view.getForwardSendBtn().setText(config.getProperty(String.class,"send"));
            }
            view.setSortingForward(command -> {
                if(command == COMMANDS.FORWARD){
                    ForwardToSortingEvent event = new ForwardToSortingEvent(sortingName, forwardFrom.getId(), massage, isForwarded);
                    listener.listen(event);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HBox gethBox() {
        return hBox;
    }
}