package org.openjfx.view.entrance.login;

import ir.sharif.ap.phase3.event.entrance.LoginEvent;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.COMMANDS;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;

public class Login {

    private final Config config = Config.getConfig("registration");
    private EventListener listener;

    public void generate(EventListener listener) {

        this.listener = listener;
        FXMLLoader loginLoader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"loginAddress")));
        try {
            Parent loginRoot = loginLoader.load();
            SceneManager.setLoginScene(new Scene(loginRoot));
            SceneManager.getMainStage().setScene(SceneManager.getLoginScene());
            setLoginListeners(loginLoader);
            SceneManager.getMainStage().show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setLoginListeners(FXMLLoader loader){
        LoginView currentView = loader.getController();
        currentView.setLoginStringListener(command -> {
            if(command == COMMANDS.GOTOREGISTER){
                try {
                    SceneManager.switchToRegister();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else if(command == COMMANDS.GOTOLOGIN) {
                LoginEvent event1 = new LoginEvent(currentView.getUsernameField().getText(), currentView.getPasswordField().getText());
                listener.listen(event1);
                currentView.getUsernameField().clear();
                currentView.getPasswordField().clear();
                currentView.getErrorLabel().setText("");
            }
        });
    }
}