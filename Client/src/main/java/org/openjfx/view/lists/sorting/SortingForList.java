package org.openjfx.view.lists.sorting;

import ir.sharif.ap.phase3.event.sorting.DeleteSortingEvent;
import ir.sharif.ap.phase3.event.sorting.ShowUserOFSortingEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;

public class SortingForList {

    private HBox hBox;

    public SortingForList(UserCopy user, String sortingName, EventListener listener) {
        Config config = Config.getConfig("lists");
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class, "sortingAddress")));
            hBox = loader.load();
            SortingForListView currentView = loader.getController();
            currentView.getSortingNameLabel().setText(sortingName);
            currentView.setSortingListener(command -> {
                if(command == COMMANDS.VIEW){
                    ShowUserOFSortingEvent event = new ShowUserOFSortingEvent(user.getId(), sortingName);
                    listener.listen(event);
                } else if (command == COMMANDS.REMOVE){
                    DeleteSortingEvent event = new DeleteSortingEvent(user.getId(), sortingName);
                    listener.listen(event);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public HBox gethBox() {
        return hBox;
    }
}