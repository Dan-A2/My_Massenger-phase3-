package org.openjfx.view.lists.sorting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.openjfx.listeners.CommandListener;
import ir.sharif.ap.phase3.util.COMMANDS;

public class SortingForListForwardSendView {

    @FXML
    private HBox hBox;

    @FXML
    private Label sortingNameLabel;

    @FXML
    private Button forwardSendBtn;

    @FXML
    void forward(ActionEvent event) {
        sortingForward.listenCommand(COMMANDS.FORWARD);
    }

    private CommandListener sortingForward;

    public void setSortingForward(CommandListener sortingForward) {
        this.sortingForward = sortingForward;
    }

    public Label getSortingNameLabel() {
        return sortingNameLabel;
    }

    public Button getForwardSendBtn() {
        return forwardSendBtn;
    }
}