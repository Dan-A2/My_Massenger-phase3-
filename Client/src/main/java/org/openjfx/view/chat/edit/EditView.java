package org.openjfx.view.chat.edit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.openjfx.listeners.CommandListener;
import ir.sharif.ap.phase3.util.COMMANDS;

public class EditView {

    @FXML
    private AnchorPane massagePane;

    @FXML
    private TextField massageField;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button editBtn;

    @FXML
    void getBack(ActionEvent event) {
        listener.listenCommand(COMMANDS.BACK);
    }

    @FXML
    void getBackMM(ActionEvent event) {
        listener.listenCommand(COMMANDS.MAINMENU);
    }

    @FXML
    void edit(ActionEvent event) {
        listener.listenCommand(COMMANDS.EDIT);
    }


    private CommandListener listener;

    public void setListener(CommandListener listener) {
        this.listener = listener;
    }

    public AnchorPane getMassagePane() {
        return massagePane;
    }

    public TextField getMassageField() {
        return massageField;
    }
}