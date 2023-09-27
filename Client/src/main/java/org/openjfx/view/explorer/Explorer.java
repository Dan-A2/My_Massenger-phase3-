package org.openjfx.view.explorer;

import ir.sharif.ap.phase3.event.general.GoToExplorerEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.view.explorer.search.Search;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.view.mainMenu.MainMenu;

public class Explorer {

    private AnchorPane pane;

    public void show(EventListener listener){
        Config config = Config.getConfig("explorer");
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class, "explorerAddress")));
        try {
            pane = loader.load();
            ExplorerView view = loader.getController();
            view.setExplorerListener(command -> {
                if (command == COMMANDS.RAND) {
                    listener.listen(new GoToExplorerEvent(MainMenu.getCurrentUser().getId()));
                } else if (command == COMMANDS.SEARCH) {
                    Search search = new Search();
                    search.show(listener);
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