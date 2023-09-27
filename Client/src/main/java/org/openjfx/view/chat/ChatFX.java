package org.openjfx.view.chat;

import ir.sharif.ap.phase3.event.chat.DeleteMassageEventChat;
import ir.sharif.ap.phase3.event.group.DeleteMassageEventGroup;
import ir.sharif.ap.phase3.event.group.SendMassageToGroupEvent;
import ir.sharif.ap.phase3.event.message.MassageEvent;
import ir.sharif.ap.phase3.event.message.SaveMassageEvent;
import ir.sharif.ap.phase3.model.help.ChatFiller;
import ir.sharif.ap.phase3.model.help.GroupFiller;
import ir.sharif.ap.phase3.model.help.MassageFiller;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.util.Updater;
import org.openjfx.view.chat.edit.Edit;
import org.openjfx.view.chat.massage.withUserPic.MassageWithUserPic;
import org.openjfx.view.chat.schedule.Schedule;

import java.io.File;

public class ChatFX {

    private final Config chatConfig = Config.getConfig("chat");
    private final FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(chatConfig.getProperty(String.class, "chatFXAddress")));
    private final EventListener listener;
    private Scene scene;
    private File data;

    public ChatFX(EventListener listener) {
        this.listener = listener;
    }

    public ChatView generateChat(int currentUserId, ChatFiller filler) {
        try {
            Parent root = loader.load();
            scene = new Scene(root);
            ChatView view = loader.getController();
            view.getScheduleBtn().setVisible(false);
            UserCopy currUser = null, otherUser = null;
            if (filler.getUser1().getId() == currentUserId) {
                view.getNameLabel().setText(filler.getUser2().getUsername());
                currUser = filler.getUser1();
                otherUser = filler.getUser2();
            } else if (filler.getUser2().getId() == currentUserId) {
                view.getNameLabel().setText(filler.getUser1().getUsername());
                currUser = filler.getUser2();
                otherUser = filler.getUser1();
            }
            if (otherUser.getProfileImage() != null) {
                ImageConvertor convertor = new ImageConvertor();
                view.getPictureCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(otherUser.getProfileImage()))));
            }
            updateChat(view, filler, currUser, otherUser);
            UserCopy finalOtherUser = otherUser;
            view.setChatListener(commands -> {
                if (commands == COMMANDS.SENDMASSAGE) {
                    sendMassage(view, currentUserId, finalOtherUser.getId());
                } else if (commands == COMMANDS.SELECTIMAGE) {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(Config.getConfig("yourAccount").getProperty("imageType"), "*.png");
                    fileChooser.getExtensionFilters().addAll(extFilterPNG);
                    try {
                        data = fileChooser.showOpenDialog(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (commands == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (commands == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                }
            });
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateChat(ChatView view, ChatFiller filler, UserCopy currUser, UserCopy other){
        Platform.runLater(() -> view.getChatGrid().getChildren().clear());
        for (MassageFiller massage : filler.getMassages()) {
            if (massage.getSender().getId() == currUser.getId()) {
                MassageWithUserPic massage1 = new MassageWithUserPic(massage);
                massage1.create(Config.getConfig("chat").getProperty(String.class, "Deletable"));
                massage1.getView().getEditBtn().setVisible(!massage.isForwarded());
                massage1.getView().getDeleteBtn().setVisible(true);
                massage1.getView().setListener(commands -> {
                    if (commands == COMMANDS.EDIT) {
                        Edit edit = new Edit();
                        edit.show(massage, listener);
                    } else if (commands == COMMANDS.DELETE) {
                        DeleteMassageEventChat event = new DeleteMassageEventChat(massage.getID(), filler.getChatId(), currUser.getId());
                        listener.listen(event);
                    } else if (commands == COMMANDS.SAVE) {
                        SaveMassageEvent event = new SaveMassageEvent(currUser.getId(), massage.getID());
                        listener.listen(event);
                    }
                });
                Platform.runLater(() -> view.getChatGrid().add(massage1.getPane(), 0, view.getChatGrid().getRowCount()+1));
                view.getChatGrid().setMargin(massage1.getPane(), new Insets(10));
            } else {
                MassageWithUserPic massage1 = new MassageWithUserPic(massage);
                massage1.create(Config.getConfig("chat").getProperty(String.class, "noDelete"));
                massage1.getView().setListener(commands -> {
                    if (commands == COMMANDS.SAVE) {
                        SaveMassageEvent event = new SaveMassageEvent(currUser.getId(), massage.getID());
                        listener.listen(event);
                    }
                });
                Platform.runLater(() -> view.getChatGrid().add(massage1.getPane(), 0, view.getChatGrid().getRowCount()+1));
                view.getChatGrid().setMargin(massage1.getPane(), new Insets(10));
            }
        }
    }

    public ChatView generateGroup(int currentUserId, GroupFiller filler, Updater updater){
        try {
            Parent root = loader.load();
            scene = new Scene(root);
            ChatView view = loader.getController();
            view.getScheduleBtn().setVisible(true);
            view.getNumberOfMembers().setVisible(true);
            view.getNumberOfMembers().setText(Integer.toString(filler.getUsers().size()));
            view.getNameLabel().setText(filler.getGroupName());
            view.getAddUserBtn().setVisible(true);
            UserCopy currentUser = null;
            for (UserCopy u : filler.getUsers()) {
                if (u.getId() == currentUserId) {
                    currentUser = u;
                    break;
                }
            }
            updateGroup(view, currentUser, filler);
            view.setChatListener(commands -> {
                if (commands == COMMANDS.SENDMASSAGE) {
                    sendMassage(view, currentUserId, filler);
                } else if (commands == COMMANDS.SELECTIMAGE) {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(Config.getConfig("yourAccount").getProperty("imageType"), "*.png");
                    fileChooser.getExtensionFilters().addAll(extFilterPNG);
                    try {
                        data = fileChooser.showOpenDialog(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (commands == COMMANDS.BACK) {
                    SceneManager.getSceneManager().getBack();
                } else if (commands == COMMANDS.MAINMENU) {
                    SceneManager.getSceneManager().backToMain();
                } else if (commands == COMMANDS.SCHEDULE) {
                    Schedule schedule = new Schedule(listener, view.getMassageArea().getText(), currentUserId, filler.getId());
                    updater.stopLoop();
                    schedule.show();
                }
            });
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateGroup(ChatView view, UserCopy currentUser, GroupFiller filler){
        for (MassageFiller massage : filler.getMassages()) {
            if (massage.getSender().getId() == currentUser.getId()) {
                MassageWithUserPic massage1 = new MassageWithUserPic(massage);
                massage1.create(Config.getConfig("chat").getProperty(String.class, "Deletable"));
                massage1.getView().getEditBtn().setVisible(!massage.isForwarded());
                massage1.getView().getDeleteBtn().setVisible(true);
                massage1.getView().setListener(commands -> {
                    if (commands == COMMANDS.EDIT) {
                        Edit edit = new Edit();
                        edit.show(massage, listener);
                    } else if (commands == COMMANDS.DELETE) {
                        DeleteMassageEventGroup event = new DeleteMassageEventGroup(filler.getId(), massage.getID());
                        listener.listen(event);
                    } else if (commands == COMMANDS.SAVE) {
                        SaveMassageEvent event = new SaveMassageEvent(currentUser.getId(), massage.getID());
                        listener.listen(event);
                    }
                });
                view.getChatGrid().add(massage1.getPane(), 0, view.getChatGrid().getRowCount()+1);
                view.getChatGrid().setMargin(massage1.getPane(), new Insets(10));
            } else {
                MassageWithUserPic massage1 = new MassageWithUserPic(massage);
                massage1.create(Config.getConfig("chat").getProperty(String.class, "noDelete"));
                massage1.getView().setListener(commands -> {
                    if (commands == COMMANDS.SAVE) {
                        SaveMassageEvent event = new SaveMassageEvent(currentUser.getId(), massage.getID());
                        listener.listen(event);
                    }
                });
                view.getChatGrid().add(massage1.getPane(), 0, view.getChatGrid().getRowCount()+1);
                view.getChatGrid().setMargin(massage1.getPane(), new Insets(10));
            }
        }
    }

    private void sendMassage(ChatView view, int senderId, int receiverId) {
        if (!view.getMassageArea().getText().equals("")) {
            MassageEvent event;
            if (data == null) {
                event = new MassageEvent(senderId, receiverId, view.getMassageArea().getText(),  false,null);
            } else {
                ImageConvertor convertor = new ImageConvertor();
                event = new MassageEvent(senderId, receiverId, view.getMassageArea().getText(), false, convertor.selectImage(data));
                data = null;
            }
            listener.listen(event);
            view.getMassageArea().clear();
        }
    }

    private void sendMassage(ChatView view, int senderId, GroupFiller filler) {
        if (!view.getMassageArea().getText().equals("")) {
            SendMassageToGroupEvent event;
            if (data == null) {
                event = new SendMassageToGroupEvent(filler.getId(), view.getMassageArea().getText(), senderId, null);
            } else {
                ImageConvertor convertor = new ImageConvertor();
                event = new SendMassageToGroupEvent(filler.getId(), view.getMassageArea().getText(), senderId, convertor.selectImage(data));
                data = null;
            }
            listener.listen(event);
            view.getMassageArea().clear();
        }
    }

    public Scene getScene() {
        return scene;
    }
}