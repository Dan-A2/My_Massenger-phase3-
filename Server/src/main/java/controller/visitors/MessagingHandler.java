package controller.visitors;

import database.Database;
import ir.sharif.ap.phase3.util.TweetPageType;
import util.TweetFillerCreator;
import util.UserCopyCreator;
import ir.sharif.ap.phase3.event.messaging.*;
import ir.sharif.ap.phase3.model.help.*;
import ir.sharif.ap.phase3.model.main.*;
import ir.sharif.ap.phase3.response.*;

import java.util.LinkedList;
import java.util.List;

public class MessagingHandler implements MessagingVisitor {

    private static MessagingHandler handler;

    public static MessagingHandler getInstance() {
        if (handler == null)
            handler = new MessagingHandler();
        return handler;
    }

    @Override
    public Response visitShowChats(GoToChatsEvent goToChatsEvent) {
        User user = Database.get(goToChatsEvent.getUserId(), User.class);
        List<ChatFiller> chatFillers = new LinkedList<>();
        for (Chat chat : user.getMyChats()) {
            chatFillers.add(new ChatFiller(chat));
        }
        return new ShowChatListResponse(chatFillers, false);
    }

    @Override
    public Response visitCreateSorting(OpenCreateSortingEvent openCreateSortingEvent) {
        User user = Database.get(openCreateSortingEvent.getId(), User.class);
        List<UserCopy> followings = new LinkedList<>();
        for (User u : user.getFollowings()) {
            followings.add(UserCopyCreator.createUser(u));
        }
        return new CreateSorting_GroupResponse(followings, true);
    }

    @Override
    public Response visitGoToNotes(GoToNotesEvent goToNotesEvent) {
        User user = Database.get(goToNotesEvent.getUserId(), User.class);
        List<MassageFiller> notes = new LinkedList<>();
        for (Message m : user.getSavedTextsOfMe()) {
            notes.add(new MassageFiller(m));
        }
        return new ShowMessageListResponse(notes, true);
    }

    @Override
    public Response visitGoToSavedMessages(GoToSavedMessagesEvent goToSavedMessagesEvent) {
        User user = Database.get(goToSavedMessagesEvent.getId(), User.class);
        List<MassageFiller> messages = new LinkedList<>();
        for (Message m : user.getSavedMassages()) {
            messages.add(new MassageFiller(m));
        }
        return new ShowMessageListResponse(messages, false);
    }

    @Override
    public Response visitGoToSavedTweets(GoToSavedTweetsEvent goToSavedTweetsEvent) {
        User user = Database.get(goToSavedTweetsEvent.getId(), User.class);
        List<TweetFiller> fillers = new LinkedList<>();
        for (Tweet_Comment t : user.getSavedTweets()) {
            fillers.add(TweetFillerCreator.createTweet(t, user));
        }
        return new TweetPageResponse(fillers, TweetPageType.Saved, false);
    }

    @Override
    public Response visitSendMessageToSorting(SendMessageToSortingEvent sendMessageToSortingEvent) {
        User user = Database.get(sendMessageToSortingEvent.getId(), User.class);
        List<SortingCopy> sortingCopies = new LinkedList<>();
        for (String sortingName : user.getMySortings().keySet()) {
            sortingCopies.add(new SortingCopy(sortingName, user.getMySortings().get(sortingName).getUsers()));
        }
        return new ShowSendMessageToSortingListResponse(sendMessageToSortingEvent.getMassage(), sortingCopies);
    }

    @Override
    public Response visitShowGroups(ShowGroupsEvent showGroupsEvent) {
        User user = Database.get(showGroupsEvent.getUserId(), User.class);
        List<GroupFiller> groupFillers = new LinkedList<>();
        for (GroupChat g : user.getGroups()) {
            groupFillers.add(new GroupFiller(g));
        }
        return new ShowGroupsResponse(groupFillers, false);
    }

    @Override
    public Response visitCreateGroup(CreateGroupEvent createGroupEvent) {
        User user = Database.get(createGroupEvent.getUserId(), User.class);
        List<UserCopy> followings = new LinkedList<>();
        for (User u : user.getFollowings()) {
            followings.add(UserCopyCreator.createUser(u));
        }
        return new CreateSorting_GroupResponse(followings, false);
    }
}
