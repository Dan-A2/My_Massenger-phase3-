package org.openjfx.view.massaging;

import ir.sharif.ap.phase3.event.message.SaveNoteEvent;
import ir.sharif.ap.phase3.event.messaging.*;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.view.mainMenu.MainMenu;

import javax.swing.*;

public class Massaging {

    private final EventListener listener;
    private AnchorPane pane;

    public Massaging(EventListener listener) {
        this.listener = listener;
    }

    public void generate(){
        Config config = Config.getConfig("massaging");
        UserCopy userCopy = MainMenu.getCurrentUser();
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"address")));
        try {
            pane = loader.load();
            MassagingView view = loader.getController();
            view.setMassagingListener(command -> {
                if (command == COMMANDS.GOTOCHATS) {
                    listener.listen(new GoToChatsEvent(userCopy.getId()));
                } else if (command == COMMANDS.CREATESORTING) {
                    listener.listen(new OpenCreateSortingEvent(userCopy.getId()));
                } else if (command == COMMANDS.GOTONOTES) {
                    listener.listen(new GoToNotesEvent(userCopy.getId()));
                } else if (command == COMMANDS.GOTOSAVEDMASSAGES) {
                    listener.listen(new GoToSavedMessagesEvent(userCopy.getId()));
                } else if (command == COMMANDS.GOTOSAVEDTWEETS) {
                    listener.listen(new GoToSavedTweetsEvent(userCopy.getId()));
                } else if (command == COMMANDS.SENDMASSAGETOSORTING) {
                    String massage = JOptionPane.showInputDialog(config.getProperty(String.class,"inputDialog"));
                    if (massage != null && !massage.equals("")) {
                        listener.listen(new SendMessageToSortingEvent(massage, userCopy.getId()));
                    }
                } else if (command == COMMANDS.SHOWGROUPS) {
                    listener.listen(new ShowGroupsEvent(userCopy.getId()));
                } else if (command == COMMANDS.CREATEGROUP) {
                    listener.listen(new CreateGroupEvent(userCopy.getId()));
                } else if (command == COMMANDS.ADDNOTE) {
                    String note = JOptionPane.showInputDialog("enter the note");
                    if (note != null && !note.equals("")) {
                        listener.listen(new SaveNoteEvent(userCopy.getId(), note));
                    }
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