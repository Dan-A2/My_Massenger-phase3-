package org.openjfx.view.entrance.register;

import ir.sharif.ap.phase3.event.entrance.RegistrationEvent;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;

public class Register {

    private EventListener listener;
    private final Config config = Config.getConfig("registration");

    public void generate(EventListener listener){

        this.listener = listener;
        try {
            FXMLLoader registerLoader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class, "registerAddress")));
            Parent registerRoot = registerLoader.load();
            SceneManager.setRegisterScene(new Scene(registerRoot));
            setRegisterListeners(registerLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRegisterListeners(FXMLLoader loader){
        RegisterView currentView = (RegisterView) loader.getController();
        currentView.setRegisterStringListener(command -> {
            if(command == COMMANDS.GOTOLOGIN){
                try {
                    SceneManager.switchToLogin();
                } catch (Exception e){e.printStackTrace();}
            } else if(command == COMMANDS.GOTOREGISTER){
                String firstname = currentView.getFirstnameField().getText();
                String lastname = currentView.getLastnameField().getText();
                String username = currentView.getUsernameField().getText();
                String password = currentView.getPasswordField().getText();
                String email = currentView.getEmailField().getText();
                String bio = currentView.getBioArea().getText();
                String phoneNumber = currentView.getPhoneNumberField().getText();
                if(firstname.equals("") || lastname.equals("") || username.equals("") || password.equals("") || email.equals("")) {
                    currentView.getErrorLabel().setText(config.getProperty(String.class,"emptyFields"));
                } else {
                    RegistrationEvent event1;
                    if (currentView.getBirthdayPicker().getValue() != null) {
                        event1 = new RegistrationEvent(firstname, lastname, username,
                                password, email, bio, phoneNumber,
                                currentView.getBirthdayPicker().getValue().toString());
                    } else {
                        event1 = new RegistrationEvent(firstname, lastname, username,
                                password, email, bio, phoneNumber,
                                "");
                    }

                    listener.listen(event1);
                    currentView.getLastnameField().clear();
                    currentView.getUsernameField().clear();
                    currentView.getFirstnameField().clear();
                    currentView.getPasswordField().clear();
                    currentView.getEmailField().clear();
                    currentView.getBioArea().clear();
                    currentView.getPhoneNumberField().clear();
                    currentView.getBirthdayPicker().setValue(null);
                }
            }
        });
    }
}