package org.openjfx.view.yourAccount.profile;

import ir.sharif.ap.phase3.event.user.ProfileEditEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import org.openjfx.SceneManager;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.connector.EventListener;
import org.openjfx.view.mainMenu.MainMenu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Profile {

    private ProfileView currentView;
    private boolean onEditMode;
    private File data;

    public void show(EventListener listener){
        Config config = Config.getConfig("yourAccount");
        FXMLLoader profileLoader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"profileAddress")));
        try {
            Parent root = profileLoader.load();
            Scene scene = new Scene(root);
            SceneManager.getSceneManager().push(scene);
            currentView = profileLoader.getController();
            UserCopy user = MainMenu.getCurrentUser();
            update();
            currentView.setProfileListener(command -> {
                if(command == COMMANDS.EDIT) {
                    setEditables(true);
                    currentView.getEditBtn().setVisible(false);
                    currentView.getSaveBtn().setVisible(true);
                } else if(command == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (command == COMMANDS.SELECTIMAGE && onEditMode) {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(config.getProperty("imageType"), "*.jpg");
                    fileChooser.getExtensionFilters().addAll(extFilterPNG);
                    try {
                        data = fileChooser.showOpenDialog(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (command == COMMANDS.SAVE) {
                    String firstname = currentView.getFirstnameField().getText();
                    String lastname = currentView.getLastnameField().getText();
                    String username = currentView.getUsernameField().getText();
                    String password = currentView.getPasswordField().getText();
                    String email = currentView.getEmailField().getText();
                    String bio = currentView.getBioArea().getText();
                    String phoneNumber = currentView.getPhonenumberField().getText();
                    if ((firstname.equals("") || lastname.equals("") || username.equals("") || password.equals("") || email.equals(""))) {
                        currentView.getErrorLabel().setText(config.getProperty(String.class, "emptyError"));
                    } else {
                        String birthday;
                        if (currentView.getBirthdayPicker().getValue() == null) {
                            birthday = null;
                        } else {
                            birthday = currentView.getBirthdayPicker().getValue().toString();
                        }
                        ProfileEditEvent event = new ProfileEditEvent(user.getId(), firstname, lastname, username,
                                password, email, bio, phoneNumber, birthday);
                        if (data != null) {
                            ImageConvertor convertor = new ImageConvertor();
                            try {
                                BufferedImage bufferedImage = ImageIO.read(data);
                                currentView.getProfileCircle().setFill(new ImagePattern(SwingFXUtils.toFXImage(bufferedImage, null)));
                                event.setImage(convertor.selectImage(data));
                                user.setProfileImage(convertor.convertByte(convertor.selectImage(data)));
                                data = null;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        listener.listen(event);
                        setEditables(false);
                        currentView.getEditBtn().setVisible(true);
                        currentView.getSaveBtn().setVisible(false);
                        update();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update(){
        UserCopy user = MainMenu.getCurrentUser();
        currentView.getFirstnameField().setText(user.getFirstName());
        currentView.getLastnameField().setText(user.getLastName());
        currentView.getUsernameField().setText(user.getUsername());
        currentView.getEmailField().setText(user.getEmail());
        currentView.getPasswordField().setText(user.getPassword());
        if (user.getProfileImage() != null) {
            ImageConvertor convertor = new ImageConvertor();
            Image image = convertor.convertData(convertor.convertString(user.getProfileImage()));
            currentView.getProfileCircle().setFill(new ImagePattern(image));
        }
        if (user.getBirthday() != null) {
            currentView.getBirthdayLabel().setText(user.getBirthday());
            currentView.getBirthdayLabel().setVisible(true);
        }
        if (user.getBio() != null) {
            currentView.getBioArea().setText(user.getBio());
        }
        if (user.getPhoneNumber() != null) {
            currentView.getPhonenumberField().setText(user.getPhoneNumber());
        }
    }

    private void setEditables(boolean b){
        currentView.getFirstnameField().setEditable(b);
        currentView.getLastnameField().setEditable(b);
        currentView.getUsernameField().setEditable(b);
        currentView.getEmailField().setEditable(b);
        currentView.getPhonenumberField().setEditable(b);
        currentView.getPasswordField().setEditable(b);
        currentView.getBirthdayPicker().setEditable(b);
        currentView.getBioArea().setEditable(b);
        currentView.getSetProfBtn().setVisible(b);
        currentView.getBirthdayLabel().setVisible(!b);
        currentView.getBirthdayPicker().setVisible(b);
        onEditMode = b;
    }
}