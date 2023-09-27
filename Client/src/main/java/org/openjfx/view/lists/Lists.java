package org.openjfx.view.lists;

import ir.sharif.ap.phase3.model.help.ChatFiller;
import ir.sharif.ap.phase3.model.help.GroupFiller;
import ir.sharif.ap.phase3.model.help.SortingCopy;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.model.main.Notification;
import ir.sharif.ap.phase3.util.COMMANDS;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ListType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.view.lists.chat.ChatForList;
import org.openjfx.view.lists.chat.GroupForList;
import org.openjfx.view.lists.sorting.SortingForList;
import org.openjfx.view.lists.sorting.SortingForListForwardSend;
import org.openjfx.view.lists.user.forward.UserForListForward;
import org.openjfx.view.lists.user.goToPage.UserForListGoToPage;
import org.openjfx.view.lists.user.requester.UserForListRequest;
import org.openjfx.view.yourAccount.notification.NotificationFX;

import java.util.ArrayList;
import java.util.List;


public class Lists {

    private final Config config = Config.getConfig("lists");
    private final FXMLLoader loader1 = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class,"listsAddress")));
    private final EventListener listener;
    private ListsView currentView;
    private Scene scene;
    private String messageToBeForwarded;
    private Parent root;

    public Lists(EventListener listener, ListType type) {
        this.listener = listener;
        try {
            root = loader1.load();
            scene = new Scene(root);
            currentView = loader1.getController();
            currentView.getTypeLabel().setText(String.valueOf(type));
            currentView.getvBox().setAlignment(Pos.CENTER);
            setListener(currentView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showUserList(UserCopy showTo, ListType type, List<UserCopy> users){
        try {
            if (type == ListType.Blocked) {
                for (UserCopy user : users) {
                    String showFrom = user.getUsername();
                    UserForListGoToPage userForListGoToPage = new UserForListGoToPage();
                    userForListGoToPage.createUser(showTo.getUsername(), showFrom, listener);
                    currentView.getvBox().getChildren().add(userForListGoToPage.getBox());
                }
            } else if (type == ListType.Followers) {
                for (UserCopy user : users) {
                    String showFrom = user.getUsername();
                    UserForListGoToPage userForListGoToPage = new UserForListGoToPage();
                    userForListGoToPage.createUser(showTo.getUsername(), showFrom, listener);
                    currentView.getvBox().getChildren().add(userForListGoToPage.getBox());
                }
            } else if (type == ListType.Followings) {
                for (UserCopy user : users) {
                    String showFrom = user.getUsername();
                    UserForListGoToPage userForListGoToPage = new UserForListGoToPage();
                    userForListGoToPage.createUser(showTo.getUsername(), showFrom, listener);
                    currentView.getvBox().getChildren().add(userForListGoToPage.getBox());
                }
            } else if (type == ListType.Forward) {
                for (UserCopy userCopy : users) {
                    UserForListForward userForListForward = new UserForListForward(showTo, userCopy, messageToBeForwarded, listener);
                    currentView.getvBox().getChildren().add(userForListForward.gethBox());
                }
            } else if (type == ListType.Requester) {
                for (UserCopy userCopy : users) {
                    UserForListRequest userForListRequest = new UserForListRequest();
                    userForListRequest.create(showTo, userCopy, listener);
                    userForListRequest.setListener(commands -> currentView.getvBox().getChildren().remove(userForListRequest.getPane()));
                    currentView.getvBox().getChildren().add(userForListRequest.getPane());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showGroupList(UserCopy showTo, List<GroupFiller> myGroups) {
        for (GroupFiller groupFiller : myGroups) {
            GroupForList groupForList = new GroupForList();
            groupForList.create(showTo, groupFiller, groupFiller.getUnseenMessages().get(showTo.getId()), listener);
            currentView.getvBox().getChildren().add(groupForList.getPane());
        }
    }

    public void showChatList(UserCopy showTo, List<ChatFiller> chats) {
        for (ChatFiller chat : chats) {
            ChatForList chatForList;
            if (chat.getUser1().getId() == showTo.getId()) {
                chatForList = new ChatForList(showTo, chat, chat.getUser1unseen(), listener);
            } else {
                chatForList = new ChatForList(showTo, chat, chat.getUser2unseen(), listener);
            }
            chatForList.show();
            currentView.getvBox().getChildren().add(chatForList.getPane());
        }
    }

    public void showNotification(List<Notification> notifications) {
        for (Notification notification : notifications) {
            NotificationFX notificationFX = new NotificationFX();
            notificationFX.create(notification);
            currentView.getvBox().getChildren().add(notificationFX.getPane());
        }
    }

    public void showSorting(UserCopy showTo, ListType type, List<SortingCopy> sortings) {
        if (type == ListType.Sorting) {
            for (SortingCopy sorting : sortings) {
                SortingForList sortingForList = new SortingForList(showTo, sorting.getSortingName(), listener);
                currentView.getvBox().getChildren().add(sortingForList.gethBox());
            }
        } else if (type == ListType.ForwardSorting) {
            for (SortingCopy sorting : sortings) {
                SortingForListForwardSend sortingForListForwardSend = new SortingForListForwardSend(showTo, sorting.getSortingName(), messageToBeForwarded, true, listener);
                currentView.getvBox().getChildren().add(sortingForListForwardSend.gethBox());
            }
        } else if (type == ListType.SendSorting) {
            for (SortingCopy sorting : sortings) {
                SortingForListForwardSend sortingForListForwardSend = new SortingForListForwardSend(showTo, sorting.getSortingName(), messageToBeForwarded, false, listener);
                currentView.getvBox().getChildren().add(sortingForListForwardSend.gethBox());
            }
        }
    }

    public void showUsersOfSorting(UserCopy showTo, List<UserCopy> users, String sortingName){
        try {
            Parent root = loader1.load();
            Scene scene = new Scene(root);
            ListsView currentView = (ListsView) loader1.getController();
            currentView.getTypeLabel().setText(sortingName);
            setListener(currentView);
            UserForListGoToPage userForListGoToPage = new UserForListGoToPage();
            for (int i = 0; i < users.size(); i++) {
                String showFrom = users.get(i).getUsername();
                userForListGoToPage.createUser(showTo.getUsername(), showFrom, listener);
                currentView.getvBox().getChildren().add(userForListGoToPage.getBox());
            }
            SceneManager.getSceneManager().push(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener(ListsView view){
        view.setListListener(command -> {
            if (command == COMMANDS.BACK) {
                SceneManager.getSceneManager().getBack();
            } else if (command == COMMANDS.MAINMENU) {
                SceneManager.getSceneManager().backToMain();
            }
        });
    }

    public void setMessageToBeForwarded(String messageToBeForwarded) {
        this.messageToBeForwarded = messageToBeForwarded;
    }

    public Parent getRoot() {
        return root;
    }

    public ListsView getCurrentView() {
        return currentView;
    }

    public Scene getScene() {
        return scene;
    }
}