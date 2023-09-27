package org.openjfx.view.explorer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.openjfx.listeners.CommandListener;
import ir.sharif.ap.phase3.util.COMMANDS;

public class ExplorerView {

    @FXML
    private AnchorPane explorerPane;

    @FXML
    private Button randTweetBtn;

    @FXML
    private Button searchBtn;

    @FXML
    void showRand(ActionEvent event) {
        explorerListener.listenCommand(COMMANDS.RAND);
    }

    @FXML
    void showSearch(ActionEvent event) {
        explorerListener.listenCommand(COMMANDS.SEARCH);
    }

    private CommandListener explorerListener;

    public void setExplorerListener(CommandListener explorerListener) {
        this.explorerListener = explorerListener;
    }

    public AnchorPane getExplorerPane() {
        return explorerPane;
    }
}