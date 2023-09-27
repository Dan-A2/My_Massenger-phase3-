package org.openjfx.view.chat.schedule;

import ir.sharif.ap.phase3.util.COMMANDS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.openjfx.listeners.CommandListener;

import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleView implements Initializable {
    @FXML
    private Button backBtn;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Button sendBtn;

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    void getBack(ActionEvent event) {
        listener.listenCommand(COMMANDS.BACK);
    }

    @FXML
    void getBackMM(ActionEvent event) {
        listener.listenCommand(COMMANDS.MAINMENU);
    }

    @FXML
    void sendPressed(ActionEvent event) {
        listener.listenCommand(COMMANDS.SENDMASSAGE);
    }

    private CommandListener listener;

    public void setListener(CommandListener listener) {
        this.listener = listener;
    }

    public int getHour() {
        return hourSpinner.getValue();
    }

    public int getMinute() {
        return minuteSpinner.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24);
        valueFactoryHour.setValue(1);
        hourSpinner.setValueFactory(valueFactoryHour);
        SpinnerValueFactory<Integer> valueFactoryMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        valueFactoryMinute.setValue(0);
        minuteSpinner.setValueFactory(valueFactoryMinute);
    }
}