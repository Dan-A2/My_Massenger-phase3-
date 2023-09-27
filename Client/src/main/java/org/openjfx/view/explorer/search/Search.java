package org.openjfx.view.explorer.search;

import ir.sharif.ap.phase3.event.user.WatchUserPageEvent;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.view.mainMenu.MainMenu;

public class Search {

    public void show(EventListener listener) {
        Config searchConfig = Config.getConfig("explorer");
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(searchConfig.getProperty(String.class, "searchAddress")));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            SearchView view = loader.getController();
            view.setSearchListener(command -> {
                if (command == COMMANDS.SEARCH) {
                    if (view.getUsernameField().getText() != null) {
                        listener.listen(new WatchUserPageEvent(MainMenu.getCurrentUser().getUsername(), view.getUsernameField().getText()));
                    }
                } else if (command == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (command == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                }
            });
            SceneManager.getSceneManager().push(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}