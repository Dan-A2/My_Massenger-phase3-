package org.openjfx.view.chat.schedule;

import ir.sharif.ap.phase3.event.message.ScheduledMassageEvent;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;

public class Schedule {

    private final EventListener listener;
    private final String txt;
    private final int senderId;
    private final int groupId;

    public Schedule(EventListener listener, String txt, int senderId, int groupId) {
        this.listener = listener;
        this.txt = txt;
        this.senderId = senderId;
        this.groupId = groupId;
    }

    public void show() {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("chat").getProperty(String.class, "scheduleAddress")));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ScheduleView view = loader.getController();
            view.setListener(command -> {
                if (command == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (command == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                } else if (command == COMMANDS.SENDMASSAGE) {
                    listener.listen(new ScheduledMassageEvent(txt, senderId, groupId, view.getHour(), view.getMinute()));
                }
            });
            SceneManager.getSceneManager().push(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}