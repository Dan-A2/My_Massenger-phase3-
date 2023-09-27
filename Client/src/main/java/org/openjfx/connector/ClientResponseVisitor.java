package org.openjfx.connector;

import ir.sharif.ap.phase3.response.*;
import org.openjfx.SceneManager;
import org.openjfx.util.Updater;
import org.openjfx.view.GraphicalAgent;
import org.openjfx.view.mainMenu.MainMenu;

public class ClientResponseVisitor implements ResponseVisitor {

    private final GraphicalAgent agent;
    private final Updater updater;
    private MainController controller;
    private int token;

    public ClientResponseVisitor(GraphicalAgent agent, Updater updater, MainController controller) {
        this.agent = agent;
        this.updater = updater;
        this.controller = controller;
    }

    @Override
    public void visitFeedback(FeedbackResponse feedbackResponse) {
        agent.showFeedback(feedbackResponse.getFeedback());
    }

    @Override
    public void visitToken(TokenResponse tokenResponse) {
        controller.setToken(tokenResponse.getToken());
        controller = null;
    }

    @Override
    public void visitGoToMain(GoToMainMenuResponse goToMainMenuResponse) {
        agent.goToMainMenu(goToMainMenuResponse.getUser());
    }

    @Override
    public void visitUserList(ShowAUserListResponse showAUserListResponse) {
        System.out.println("showing user list");
        agent.showUserList(showAUserListResponse.getListFillerUser(), showAUserListResponse.getShowTo(), showAUserListResponse.isUpdate());
        if (!showAUserListResponse.isUpdate()) {
            switch (showAUserListResponse.getListFillerUser().getType()) {
                case Followers -> updater.updateFollowers(showAUserListResponse.getShowTo().getId());
                case Followings -> updater.updateFollowings(showAUserListResponse.getShowTo().getId());
                case Requester -> updater.updateRequesters(showAUserListResponse.getShowTo().getId());
            }
        }
    }

    @Override
    public void visitShowSorting(UserSortingsResponse userSortingsResponse) {
        agent.showSorting(userSortingsResponse.getSortings(), userSortingsResponse.getThisUser());
    }

    @Override
    public void visitShowNotif(NotificationsResponse notificationsResponse) {
        agent.showNotifications(notificationsResponse.getNotifications());
    }

    @Override
    public void visitWatchPage(WatchUserPageResponse watchUserPageResponse) {
        System.out.println("going to user page");
        agent.watchUserPage(watchUserPageResponse.getFiller(), watchUserPageResponse.isUpdate());
        if (!watchUserPageResponse.isUpdate()) {
            updater.updateWatchPage(watchUserPageResponse.getFiller().getShowTo().getId(), watchUserPageResponse.getFiller().getShowFrom().getId());
        }
    }

    @Override
    public void visitTweetPage(TweetPageResponse tweetPageResponse) {
        agent.GoToTweetPage(tweetPageResponse.getTweets(), tweetPageResponse.isUpdate());
        if (!tweetPageResponse.isUpdate()) {
            switch (tweetPageResponse.getType()) {
                case Explorer -> updater.updateExplorer(MainMenu.getCurrentUser().getId());
                case TimelineFollowing -> updater.updateTimelineFollowing(MainMenu.getCurrentUser().getId());
                case TimelineLiked -> updater.updateTimelineLiked(MainMenu.getCurrentUser().getId());
                case Saved -> updater.updateSavedTweets(MainMenu.getCurrentUser().getId());
                case MyTweets -> updater.updateMyTweets(MainMenu.getCurrentUser().getId());
            }
        }
    }

    @Override
    public void visitChangeSetting(SettingChangeResponse settingChangeResponse) {
        MainMenu.setCurrentUser(settingChangeResponse.getUserCopy());
    }

    @Override
    public void visitShowComments(ShowCommentsResponse showCommentsResponse) {
        agent.showComments(showCommentsResponse.getFiller(), showCommentsResponse.isUpdate());
        if (!showCommentsResponse.isUpdate()) {
            updater.updateComments(MainMenu.getCurrentUser().getId(), showCommentsResponse.getFiller().getMotherTweet().getID());
        }
    }

    @Override
    public void visitShowChatList(ShowChatListResponse showChatListResponse) {
        agent.showChatList(showChatListResponse.getChatFillers(), showChatListResponse.isUpdate());
        if (!showChatListResponse.isUpdate()) {
            updater.updateChatList(MainMenu.getCurrentUser().getId());
        }
    }

    @Override
    public void visitCreateSorting(CreateSorting_GroupResponse createSortingResponse) {
        if (createSortingResponse.isSorting()) {
            agent.showCreateSorting(createSortingResponse.getFollowings());
        } else {
            agent.showCreateGroup(createSortingResponse.getFollowings());
        }
    }

    @Override
    public void visitShowMessageList(ShowMessageListResponse showMessageListResponse) {
        if (showMessageListResponse.isNotes()) {
            agent.showSavedNotes(showMessageListResponse.getFillers());
        } else {
            agent.showSavedMessages(showMessageListResponse.getFillers());
        }
    }

    @Override
    public void visitShowSendMessageToSortingList(ShowSendMessageToSortingListResponse showSendMessageToSortingListResponse) {
        agent.showSendMessageToASorting(showSendMessageToSortingListResponse.getMessage(), showSendMessageToSortingListResponse.getSortings());
    }

    @Override
    public void visitShowGroups(ShowGroupsResponse showGroupsResponse) {
        agent.showGroups(showGroupsResponse.getGroupFillers(), showGroupsResponse.isUpdate());
        if (!showGroupsResponse.isUpdate()) {
            updater.updateGroupList(MainMenu.getCurrentUser().getId());
        }
    }

    @Override
    public void visitShowUserForwardList(ShowUserForwardListResponse showUserForwardListResponse) {
        agent.showUserForwardList(showUserForwardListResponse.getFollowings(), showUserForwardListResponse.getMessageToBeForwarded());
    }

    @Override
    public void visitShowSortingForwardList(ShowSortingForwardListResponse showSortingForwardListResponse) {
        agent.showSortingForwardList(showSortingForwardListResponse.getSortingCopies(), showSortingForwardListResponse.getMessageToBeForwarded());
    }

    @Override
    public void visitShowGroup(ShowGroupResponse showGroupResponse) {
        agent.showGroup(showGroupResponse.getFiller(), showGroupResponse.isUpdate(), updater);
        if (!showGroupResponse.isUpdate()) {
            updater.updateGroupChat(MainMenu.getCurrentUser().getId(), showGroupResponse.getFiller().getId());
        }
    }

    @Override
    public void visitViewChat(ViewChatResponse viewChatResponse) {
        agent.showChat(viewChatResponse.getFiller(), viewChatResponse.isUpdate());
        if (!viewChatResponse.isUpdate()) {
            updater.updateChat(viewChatResponse.getFiller().getChatId());
        }
    }
}