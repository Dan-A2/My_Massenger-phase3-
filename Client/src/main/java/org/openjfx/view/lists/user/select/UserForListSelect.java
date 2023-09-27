package org.openjfx.view.lists.user.select;

import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.openjfx.SceneManager;
import org.openjfx.listeners.CommandListener;
import ir.sharif.ap.phase3.util.COMMANDS;

public class UserForListSelect {

    private HBox box;
    private boolean selected;

    public void create(UserCopy user){

        Config config = Config.getConfig("lists");
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"userSelectAddress")));
        selected = false;
        try {
            box = loader.load();
            UserForListSelectView view = loader.getController();
            update(view);
            view.getUsernameLabel().setText(user.getUsername());
            view.setListener(command -> {
                if (command == COMMANDS.SELECT) {
                    selected = true;
                } else if (command == COMMANDS.DESELECT) {
                    selected = false;
                }
                selectListener.listenCommand(command);
                update(view);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void update(UserForListSelectView view){

        if (selected) {
            view.getSelectBtn().setVisible(false);
            view.getDeselectBtn().setVisible(true);
        } else {
            view.getSelectBtn().setVisible(true);
            view.getDeselectBtn().setVisible(false);
        }

    }

    private CommandListener selectListener;

    public void setSelectListener(CommandListener selectListener) {
        this.selectListener = selectListener;
    }

    public HBox getBox() {
        return box;
    }
}