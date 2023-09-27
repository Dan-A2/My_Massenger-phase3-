package org.openjfx;

import ir.sharif.ap.phase3.util.Config;
import javafx.application.Application;
import javafx.stage.Stage;
import org.openjfx.connector.MainController;
import org.openjfx.connector.network.SocketEventSender;

import java.net.Socket;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Config config = Config.getConfig("connection");
            Socket socket = new Socket(config.getProperty(String.class, "host"), config.getProperty(Integer.class, "port"));
            MainController controller = new MainController(new SocketEventSender(socket), primaryStage);
            controller.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}