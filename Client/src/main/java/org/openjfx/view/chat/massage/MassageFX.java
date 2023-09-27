package org.openjfx.view.chat.massage;

import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.MessageStatus;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.listeners.CommandListener;

public class MassageFX {

    private AnchorPane massagePane;
    private CommandListener listener;

    public MassageFX(String txt) {
        Config massageFXConfig = Config.getConfig("chat");
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(massageFXConfig.getProperty(String.class, "massageFXAddress")));
        try {
            massagePane = loader.load();
            MassageView view = loader.getController();
            view.setListener(command -> listener.listenCommand(command));
        } catch (Exception e) {
            e.printStackTrace();
        }
        MassageView view = loader.getController();
        view.getMassageLabel().setText(txt);
    }

    public AnchorPane getMassagePane() {
        return massagePane;
    }

    public void setListener(CommandListener listener) {
        this.listener = listener;
    }
}