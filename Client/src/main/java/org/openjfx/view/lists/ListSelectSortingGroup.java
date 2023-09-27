package org.openjfx.view.lists;

import ir.sharif.ap.phase3.event.group.CreateGroupEvent;
import ir.sharif.ap.phase3.event.sorting.CreateSortingEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.view.lists.user.select.UserForListSelect;
import ir.sharif.ap.phase3.util.COMMANDS;
import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class ListSelectSortingGroup {

    private final Config config = Config.getConfig("lists");
    private final FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"listSortingAddress")));
    private List<Integer> usersId = new LinkedList<>();
    private EventListener listener;
    private UserCopy userCopy;
    private Scene scene;

    public void generate(EventListener listener, UserCopy user, List<UserCopy> followings, boolean isSorting){
        this.listener = listener;
        userCopy = user;
        try {
            Parent root = loader.load();
            scene = new Scene(root);
            ListSelectSortingGroupView view = loader.getController();
            view.getvBox().setAlignment(Pos.CENTER);
            view.getTypeLabel().setText(config.getProperty(String.class,"listSelectTitle"));
            setListener(view, isSorting);
            for (int i = 0; i < followings.size(); i++) {
                UserCopy user1 = followings.get(i);
                UserForListSelect userForListSelect = new UserForListSelect();
                userForListSelect.create(user1);
                view.getvBox().getChildren().add(userForListSelect.getBox());
                userForListSelect.setSelectListener(command -> {
                    if (command == COMMANDS.SELECT) {
                        usersId.add(user1.getId());
                    } else if (command == COMMANDS.DESELECT){
                        usersId.remove(new Integer(user1.getId()));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener(ListSelectSortingGroupView view, boolean isSorting){
        view.setListener(command -> {
            if (command == COMMANDS.BACK) {
                SceneManager.getSceneManager().getBack();
            } else if (command == COMMANDS.MAINMENU) {
                SceneManager.getSceneManager().backToMain();
            } else if (command == COMMANDS.CREATE) {
                if (isSorting) {
                    createSorting(view);
                } else {
                    createGroup(view);
                }
            }
        });
    }

    private void createSorting(ListSelectSortingGroupView view) {
        if (usersId.size() <= 1) {
            JOptionPane.showMessageDialog(null, config.getProperty(String.class,"lowUsers"));
        } else if (view.getSortingNameField().getText().equals("")) {
            JOptionPane.showMessageDialog(null, config.getProperty(String.class,"empty"));
        } else {
            CreateSortingEvent event = new CreateSortingEvent(usersId, view.getSortingNameField().getText(), userCopy.getId());
            listener.listen(event);
        }
    }

    private void createGroup(ListSelectSortingGroupView view) {
        if (usersId.size() <= 1) {
            JOptionPane.showMessageDialog(null, config.getProperty(String.class,"lowUsers"));
        } else if (view.getSortingNameField().getText().equals("")) {
            JOptionPane.showMessageDialog(null, config.getProperty(String.class,"empty"));
        } else {
            CreateGroupEvent event = new CreateGroupEvent(userCopy.getId(), usersId, view.getSortingNameField().getText());
            listener.listen(event);
        }
    }

    public Scene getScene() {
        return scene;
    }
}