package org.openjfx.view.mainMenu;

import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.view.explorer.Explorer;
import org.openjfx.view.massaging.Massaging;
import org.openjfx.view.setting.Setting;
import org.openjfx.view.timeline.Timeline;
import org.openjfx.view.yourAccount.youeAccount.YourAccount;

public class MainMenu {

    private static UserCopy currentUserr;
    private Scene menuScene;

    public MainMenu(UserCopy currentUser, EventListener listener) {
        Config config = Config.getConfig("mainmenu");
        currentUserr = currentUser;
        try {
            FXMLLoader mainMenuLoader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class, "address")));
            Parent mainMenuRoot = mainMenuLoader.load();
            menuScene = new Scene(mainMenuRoot);
            MainMenuView currentView = mainMenuLoader.getController();
            AnchorPane pane = currentView.getMainPane();
            currentView.setMainMenuListener(command -> {
                if (command == COMMANDS.GOTOPROFILE) {
                    pane.getChildren().clear();
                    YourAccount yourAccount = new YourAccount(listener);
                    yourAccount.create();
                    pane.getChildren().add(yourAccount.getPane());
                    SceneManager.getSceneManager().push(menuScene);
                } else if(command == COMMANDS.GOTOEXPLORER) {
                    pane.getChildren().clear();
                    Explorer explorer = new Explorer();
                    explorer.show(listener);
                    pane.getChildren().add(explorer.getPane());
                    SceneManager.getSceneManager().push(menuScene);
                } else if(command == COMMANDS.GOTOTIMELINE) {
                    pane.getChildren().clear();
                    Timeline timeline = new Timeline();
                    timeline.generate(listener);
                    pane.getChildren().add(timeline.getPane());
                    SceneManager.getSceneManager().push(menuScene);
                } else if(command == COMMANDS.GOTOMASSAGING) {
                    pane.getChildren().clear();
                    Massaging massaging = new Massaging(listener);
                    massaging.generate();
                    pane.getChildren().add(massaging.getPane());
                    SceneManager.getSceneManager().push(menuScene);
                } else if(command == COMMANDS.GOTOSETTING) {
                    pane.getChildren().clear();
                    Setting setting = new Setting(listener);
                    setting.create();
                    pane.getChildren().add(setting.getPane());
                    SceneManager.getSceneManager().push(menuScene);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserCopy getCurrentUser() {
        return currentUserr;
    }

    public static void setCurrentUser(UserCopy currentUserr) {
        System.out.println("changing currentUser");
        MainMenu.currentUserr = currentUserr;
    }

    public Scene getMenuScene() {
        return menuScene;
    }
}