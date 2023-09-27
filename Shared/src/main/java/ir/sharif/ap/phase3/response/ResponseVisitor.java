package ir.sharif.ap.phase3.response;

public interface ResponseVisitor {
    void visitViewChat(ViewChatResponse response);
    void visitFeedback(FeedbackResponse response);
    void visitToken(TokenResponse response);
    void visitGoToMain(GoToMainMenuResponse response);
    void visitUserList(ShowAUserListResponse response);
    void visitShowSorting(UserSortingsResponse response);
    void visitShowNotif(NotificationsResponse response);
    void visitWatchPage(WatchUserPageResponse response);
    void visitTweetPage(TweetPageResponse response);
    void visitChangeSetting(SettingChangeResponse response);
    void visitShowComments(ShowCommentsResponse response);
    void visitShowChatList(ShowChatListResponse response);
    void visitCreateSorting(CreateSorting_GroupResponse response);
    void visitShowMessageList(ShowMessageListResponse response);
    void visitShowSendMessageToSortingList(ShowSendMessageToSortingListResponse response);
    void visitShowGroups(ShowGroupsResponse response);
    void visitShowUserForwardList(ShowUserForwardListResponse response);
    void visitShowSortingForwardList(ShowSortingForwardListResponse response);
    void visitShowGroup(ShowGroupResponse response);
}