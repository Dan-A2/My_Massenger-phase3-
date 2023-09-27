package org.openjfx.view.setting;

import ir.sharif.ap.phase3.event.entrance.LogoutEvent;
import ir.sharif.ap.phase3.event.user.ChangeSettingsEvent;
import ir.sharif.ap.phase3.event.user.DeleteUserEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.view.mainMenu.MainMenu;

public class Setting {

    private final EventListener listener;
    private AnchorPane pane;
    private final Config config = Config.getConfig("setting");

    public Setting(EventListener listener) {
        this.listener = listener;
    }

    public void create() {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"address")));
        try {
            pane = loader.load();
            SettingView view = loader.getController();
            update(view);
            setListener(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener(SettingView view) {
        view.setSettingListener(command -> {
            if (command == COMMANDS.EDIT) {
                view.getEditBtn().setVisible(false);
                view.getSaveBtn().setVisible(true);
            } else if (command == COMMANDS.LOGOUT) {
                listener.listen(new LogoutEvent(MainMenu.getCurrentUser().getId()));
                System.exit(0);
            } else if (command == COMMANDS.SAVE) {
                view.getEditBtn().setVisible(true);
                view.getSaveBtn().setVisible(false);
                save(view);
            } else if (command == COMMANDS.DELETE) {
                DeleteUserEvent event = new DeleteUserEvent(MainMenu.getCurrentUser().getId());
                listener.listen(event);
            }
        });
    }

    private void save(SettingView view) {
        boolean accountPublicity;
        boolean accountActivity;
        String whoCanSeeL = "";
        accountPublicity = view.getPublicRadioBtn().isSelected();
        if (view.getEveryOneRBtn().isSelected()) {
            whoCanSeeL = config.getProperty(String.class,"everybody");
        } else if (view.getFollowersRBtn().isSelected()) {
            whoCanSeeL = config.getProperty(String.class,"followers");
        } else if (view.getNobodyRBtn().isSelected()){
            whoCanSeeL = config.getProperty(String.class,"nobody");
        }
        accountActivity = view.getActiveRBtn().isSelected();
        ChangeSettingsEvent event = new ChangeSettingsEvent(MainMenu.getCurrentUser().getId(), accountPublicity, whoCanSeeL, accountActivity);
        listener.listen(event);
    }

    private void update(SettingView view) {
        UserCopy user = MainMenu.getCurrentUser();
        if (user.isAccountPublic()) {
            view.getPublicRadioBtn().setSelected(true);
        } else {
            view.getPrivateRadioBtn().setSelected(true);
        }
        if (user.getWhoCanSeeLastSeen().equals(config.getProperty(String.class,"everybody"))) {
            view.getEveryOneRBtn().setSelected(true);
        } else if (user.getWhoCanSeeLastSeen().equals(config.getProperty(String.class,"followers"))) {
            view.getFollowersRBtn().setSelected(true);
        } else { //nobody
            view.getNobodyRBtn().setSelected(true);
        }
        if (user.isActive()) {
            view.getActiveRBtn().setSelected(true);
        } else {
            view.getInactiveRBtn().setSelected(true);
        }
    }

    public AnchorPane getPane() {
        return pane;
    }
}