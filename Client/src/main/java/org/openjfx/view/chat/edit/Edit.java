package org.openjfx.view.chat.edit;

import ir.sharif.ap.phase3.event.message.EditMassageEvent;
import ir.sharif.ap.phase3.model.help.MassageFiller;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;

import javax.swing.*;

public class Edit {

    public void show(MassageFiller massage, EventListener listener) {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("chat").getProperty(String.class,"editAddress")));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            EditView view = loader.getController();
            setListener(view, massage, listener);
            SceneManager.getSceneManager().push(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener(EditView view, MassageFiller massage, EventListener listener){
        view.setListener(commands -> {
            if (commands == COMMANDS.EDIT) {
                if (view.getMassageField().getText().equals("")) {
                    JOptionPane.showMessageDialog(null, Config.getConfig("chat").getProperty(String.class,"editErrorMassage"));
                } else {
                    EditMassageEvent event = new EditMassageEvent(massage.getID(), view.getMassageField().getText());
                    listener.listen(event);
                    view.getMassageField().clear();
                }
            } else if (commands == COMMANDS.BACK) {
                SceneManager.getSceneManager().getBack();
            } else if (commands == COMMANDS.MAINMENU) {
                SceneManager.getSceneManager().backToMain();
            }
        });
    }

}