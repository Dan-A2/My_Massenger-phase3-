package org.openjfx.view.chat.massage.withUserPic;

import ir.sharif.ap.phase3.model.help.MassageFiller;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import org.openjfx.SceneManager;

public class MassageWithUserPic {

    private final MassageFiller massage;
    private AnchorPane pane;
    private MassageWithUserPicView view;

    public MassageWithUserPic(MassageFiller massage) {
        this.massage = massage;
    }

    public void create(String type) {
        Config massageWithPicConfig = Config.getConfig("chat");
        FXMLLoader loader1 = new FXMLLoader(SceneManager.class.getResource(massageWithPicConfig.getProperty(String.class,"massageWithPicAddress")));
        FXMLLoader loader2 = new FXMLLoader(SceneManager.class.getResource(massageWithPicConfig.getProperty(String.class,"massageWithImageAddress")));
        try {
            if (massage.getImage() != null) {
                pane = loader2.load();
                MassageWithImageView view1 = loader2.getController();
                ImageConvertor convertor = new ImageConvertor();
                view1.getImageView().setImage(convertor.convertData(convertor.convertString(massage.getImage())));
                view = view1;
            } else {
                pane = loader1.load();
                view = loader1.getController();
            }
            switch (massage.getStatus()) {
                case Sent -> view.getPane().setStyle("-fx-background-color: " +
                        massageWithPicConfig.getProperty(String.class, "greenHex") + ";");
                case Delivered -> view.getPane().setStyle("-fx-background-color: " +
                        massageWithPicConfig.getProperty(String.class, "redHex") + ";");
                // Group & Seen
                default -> view.getPane().setStyle("-fx-background-color: " +
                        massageWithPicConfig.getProperty(String.class, "blueHex") + ";");
            }
            view.getMassageLabel().setText(massage.getMassage());
            view.getUsernameLabel().setText(massage.getSender().getUsername());
            if (massage.getSender().getProfileImage() != null) {
                ImageConvertor convertor = new ImageConvertor();
                view.getCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(massage.getSender().getProfileImage()))));
            }
            if (type.equals(massageWithPicConfig.getProperty("noDelete"))) {
                view.getDeleteBtn().setVisible(false);
                view.getEditBtn().setVisible(false);
            } else {
                view.getDeleteBtn().setVisible(true);
                view.getEditBtn().setVisible(true);
            }
            view.getSaveBtn().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getPane() {
        return pane;
    }

    public MassageWithUserPicView getView() {
        return view;
    }
}