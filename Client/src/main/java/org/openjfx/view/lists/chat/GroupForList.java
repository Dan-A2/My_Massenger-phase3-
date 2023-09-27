package org.openjfx.view.lists.chat;

import ir.sharif.ap.phase3.event.group.GroupLeaveEvent;
import ir.sharif.ap.phase3.event.group.ShowGroupEvent;
import ir.sharif.ap.phase3.model.help.GroupFiller;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.connector.EventListener;

public class GroupForList {

    private AnchorPane pane;

    public void create(UserCopy showTo, GroupFiller filler, int unseenMassage, EventListener listener) {

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("lists").getProperty(String.class,"groupForList")));
        try {
            pane = loader.load();
            GroupForListView view = loader.getController();
            view.getGroupNameLabel().setText(filler.getGroupName());
            view.getUnseenLabel().setText(Integer.toString(unseenMassage));
            view.setListener(commands -> {
                if (commands == COMMANDS.LEAVE) {
                    GroupLeaveEvent event = new GroupLeaveEvent(showTo.getId(), filler.getId());
                    listener.listen(event);
                } else if (commands == COMMANDS.VIEW) {
                    ShowGroupEvent event = new ShowGroupEvent(showTo.getId(), filler);
                    listener.listen(event);
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