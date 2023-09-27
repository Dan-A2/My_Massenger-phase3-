package ir.sharif.ap.phase3.event.messaging;

import ir.sharif.ap.phase3.response.Response;

public interface MessagingVisitor {
    Response visitShowChats(GoToChatsEvent event);
    Response visitCreateSorting(OpenCreateSortingEvent event);
    Response visitGoToNotes(GoToNotesEvent event);
    Response visitGoToSavedMessages(GoToSavedMessagesEvent event);
    Response visitGoToSavedTweets(GoToSavedTweetsEvent event);
    Response visitSendMessageToSorting(SendMessageToSortingEvent event);
    Response visitShowGroups(ShowGroupsEvent event);
    Response visitCreateGroup(CreateGroupEvent event);
}