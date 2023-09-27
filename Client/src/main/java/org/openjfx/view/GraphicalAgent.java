package org.openjfx.view;

import ir.sharif.ap.phase3.model.help.*;
import ir.sharif.ap.phase3.model.main.Notification;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.Feedback;
import ir.sharif.ap.phase3.util.ListType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import org.openjfx.util.Updater;
import org.openjfx.view.chat.ChatFX;
import org.openjfx.view.chat.ChatView;
import org.openjfx.view.lists.ListSelectSortingGroup;
import org.openjfx.view.lists.Lists;
import org.openjfx.view.mainMenu.MainMenu;
import org.openjfx.view.tweet.TweetPage;
import org.openjfx.view.tweet.comment.CommentsPage;
import org.openjfx.view.watchUserPage.WatchUserPage;

import javax.swing.*;
import java.util.List;

public class GraphicalAgent {

    private final EventListener listener;
    private final Stage primaryStage;
    private ChatView currentChat;
    private ChatFX chatFX;

    public GraphicalAgent(EventListener listener, Stage primaryStage) {
        this.listener = listener;
        this.primaryStage = primaryStage;
        SceneManager manager = SceneManager.getSceneManager();
        SceneManager.setMainStage(primaryStage);
        manager.setListener(listener);
    }

    public void initialize() {
        Platform.runLater(primaryStage::show);
    }

    public void goToRegister() {
        SceneManager.switchToRegister();
    }

    public void goToLogin() {
        SceneManager.switchToLogin();
    }

    public void goToMainMenu(UserCopy userCopy) {
        MainMenu mainMenu = new MainMenu(userCopy, listener);
        SceneManager.getSceneManager().getScenes().clear();
        SceneManager.getSceneManager().push(mainMenu.getMenuScene());
    }

    public void showFeedback(Feedback feedback) {
        Config config = Config.getConfig("feedback");
        switch (feedback) {
            case Login -> JOptionPane.showMessageDialog(null, config.getPropertyArray(String.class, "login"));
            case Register -> JOptionPane.showMessageDialog(null, config.getPropertyArray(String.class, "register"));
            case TweetPosted -> JOptionPane.showMessageDialog(null, config.getPropertyArray(String.class, "tweetP"));
            case WrongUsername -> JOptionPane.showMessageDialog(null, config.getPropertyArray(String.class, "wrongUN"));
            case ScheduleConfirm -> JOptionPane.showMessageDialog(null, config.getPropertyArray(String.class, "schedule"));
            case RegisterAt -> JOptionPane.showMessageDialog(null, config.getPropertyArray(String.class, "registerAt"));
        }
    }

    public void showUserList(ListFillerUser listFillerUser, UserCopy showTo, boolean isUpdate) {
        if (isUpdate) {
            SceneManager.getSceneManager().pop();
        }
        Lists lists = new Lists(listener, listFillerUser.getType());
        lists.showUserList(showTo, listFillerUser.getType(), listFillerUser.getUsers());
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void showSorting(List<SortingCopy> sortings, UserCopy showTo) {
        Lists lists = new Lists(listener, ListType.Sorting);
        lists.showSorting(showTo, ListType.Sorting, sortings);
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void showNotifications(List<Notification> notifications) {
        Lists lists = new Lists(listener, ListType.Notification);
        lists.showNotification(notifications);
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void watchUserPage(WatchUserPageFiller filler, boolean isUpdate) {
        if (isUpdate) {
            SceneManager.getSceneManager().pop();
        }
        WatchUserPage watchUserPage = new WatchUserPage(filler, listener);
        watchUserPage.show();
        SceneManager.getSceneManager().push(watchUserPage.getScene());
    }

    public void GoToTweetPage(List<TweetFiller> tweetFillers, boolean isUpdate) {
        if (isUpdate) {
            SceneManager.getSceneManager().pop();
        }
        TweetPage page = new TweetPage(listener);
        page.showInGeneral(tweetFillers);
        SceneManager.getSceneManager().push(page.getScene());
    }

    public void showComments(CommentPageFiller filler, boolean isUpdate) {
        if (isUpdate) {
            SceneManager.getSceneManager().pop();
        }
        CommentsPage commentsPage = new CommentsPage(filler, listener);
        commentsPage.create();
        SceneManager.getSceneManager().push(commentsPage.getScene());
    }

    public void showChatList(List<ChatFiller> chatFillers, boolean isUpdate) {
        if (isUpdate) {
            SceneManager.getSceneManager().pop();
        }
        Lists lists = new Lists(listener, ListType.Chats);
        lists.showChatList(MainMenu.getCurrentUser(), chatFillers);
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void showCreateSorting(List<UserCopy> users) {
        ListSelectSortingGroup listSelectSortingGroup = new ListSelectSortingGroup();
        listSelectSortingGroup.generate(listener, MainMenu.getCurrentUser(), users, true);
        SceneManager.getSceneManager().push(listSelectSortingGroup.getScene());
    }

    public void showSavedMessages(List<MassageFiller> fillers) {
        TweetPage tweetPage = new TweetPage(listener);
        tweetPage.showSavedMassages(fillers);
        SceneManager.getSceneManager().push(tweetPage.getScene());
    }

    public void showSavedNotes(List<MassageFiller> fillers) {
        TweetPage tweetPage = new TweetPage(listener);
        tweetPage.showSavedNotes(fillers);
        SceneManager.getSceneManager().push(tweetPage.getScene());
    }

    public void showSendMessageToASorting(String message, List<SortingCopy> sortingCopies) {
        Lists lists = new Lists(listener, ListType.SendSorting);
        lists.setMessageToBeForwarded(message);
        lists.showSorting(MainMenu.getCurrentUser(), ListType.SendSorting, sortingCopies);
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void showGroups(List<GroupFiller> groupFillers, boolean isUpdate) {
        if (isUpdate) {
            SceneManager.getSceneManager().pop();
        }
        Lists lists = new Lists(listener, ListType.Group);
        lists.showGroupList(MainMenu.getCurrentUser(), groupFillers);
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void showCreateGroup(List<UserCopy> users) {
        ListSelectSortingGroup listSelectSortingGroup = new ListSelectSortingGroup();
        listSelectSortingGroup.generate(listener, MainMenu.getCurrentUser(), users, false);
        SceneManager.getSceneManager().push(listSelectSortingGroup.getScene());
    }

    public void showUserForwardList(List<UserCopy> followings, String message) {
        Lists lists = new Lists(listener, ListType.Forward);
        lists.setMessageToBeForwarded(message);
        lists.showUserList(MainMenu.getCurrentUser(), ListType.Forward, followings);
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void showSortingForwardList(List<SortingCopy> sortingCopies, String message) {
        Lists lists = new Lists(listener, ListType.ForwardSorting);
        lists.setMessageToBeForwarded(message);
        lists.showSorting(MainMenu.getCurrentUser(), ListType.ForwardSorting, sortingCopies);
        SceneManager.getSceneManager().push(lists.getScene());
    }

    public void showChat(ChatFiller chatFiller, boolean isUpdate) {
        if (isUpdate) {
            if (chatFiller.getUser1().getId() == MainMenu.getCurrentUser().getId()) {
                chatFX.updateChat(currentChat, chatFiller, chatFiller.getUser1(), chatFiller.getUser2());
            } else {
                chatFX.updateChat(currentChat, chatFiller, chatFiller.getUser2(), chatFiller.getUser1());
            }
        } else {
            chatFX = new ChatFX(listener);
            currentChat = chatFX.generateChat(MainMenu.getCurrentUser().getId(), chatFiller);
            SceneManager.getSceneManager().push(chatFX.getScene());
        }
    }

    public void showGroup(GroupFiller groupFiller, boolean isUpdate, Updater updater) {
        if (isUpdate) {
            chatFX.updateGroup(currentChat, MainMenu.getCurrentUser(), groupFiller);
        } else {
            chatFX = new ChatFX(listener);
            currentChat = chatFX.generateGroup(MainMenu.getCurrentUser().getId(), groupFiller, updater);
            SceneManager.getSceneManager().push(chatFX.getScene());
        }
    }
}