package controller.visitors;

import database.Database;
import ir.sharif.ap.phase3.event.general.*;
import ir.sharif.ap.phase3.model.help.*;
import ir.sharif.ap.phase3.model.main.Chat;
import ir.sharif.ap.phase3.model.main.GroupChat;
import ir.sharif.ap.phase3.model.main.Tweet_Comment;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.response.*;
import ir.sharif.ap.phase3.util.Checker;
import ir.sharif.ap.phase3.util.ListType;
import ir.sharif.ap.phase3.util.Status;
import ir.sharif.ap.phase3.util.TweetPageType;
import logic.ChatController;
import logic.GroupController;
import logic.Tweet_CommentController;
import util.TweetFillerCreator;
import util.UserCopyCreator;

import java.util.LinkedList;
import java.util.List;

public class GeneralEventsHandler implements GeneralVisitor {

    private static GeneralEventsHandler generalEventsHandler;

    private GeneralEventsHandler() {}

    public static GeneralEventsHandler getHandler() {
        if (generalEventsHandler == null)
            generalEventsHandler = new GeneralEventsHandler();
        return generalEventsHandler;
    }

    @Override
    public Response visitUpdate(UpdateRequest updateRequest) {
        switch (updateRequest.getStatus()) {
            case TimelineLiked:
                return timelinePage(Integer.parseInt(updateRequest.getOza()), true, true);
            case TimelineFollowing:
                return timelinePage(Integer.parseInt(updateRequest.getOza()), true, false);
            case Explorer:
                return explorerPage(Integer.parseInt(updateRequest.getOza()), true);
            case Chat:
                String oza = updateRequest.getOza();
                int userId2 = Integer.parseInt(oza.substring(0, oza.indexOf(',')));
                oza = oza.substring(oza.indexOf(',') + 1);
                int chatId2 = Integer.parseInt(oza);
                Chat chat = Database.get(chatId2, Chat.class);
                User tmp = Database.get(userId2, User.class);
                new ChatController().resetMassages(chat, tmp);
                return new ViewChatResponse(new ChatFiller(chat), true);
            case GroupChat:
                String data = updateRequest.getOza();
                int userId = 0, groupId = 0;
                for (int i = 0; i < data.length(); i++) {
                    if (data.charAt(i) == ',') {
                        userId = Integer.parseInt(data.substring(0, i));
                        groupId = Integer.parseInt(data.substring(i+1));
                    }
                }
                User user = Database.get(userId, User.class);
                GroupChat groupChat = Database.get(groupId, GroupChat.class);
                GroupController controller = new GroupController();
                controller.resetMassages(user, groupChat);
                return new ShowGroupResponse(new GroupFiller(groupChat), true);
            case Followers:
                User user1 = Database.get(Integer.parseInt(updateRequest.getOza()), User.class);
                return new ShowAUserListResponse(new ListFillerUser(ListType.Followers, user1.getFollowers()), UserCopyCreator.createUser(user1), true);
            case Followings:
                User user2 = Database.get(Integer.parseInt(updateRequest.getOza()), User.class);
                return new ShowAUserListResponse(new ListFillerUser(ListType.Followings, user2.getFollowings()), UserCopyCreator.createUser(user2), true);
            case Requesters:
                User user3 = Database.get(Integer.parseInt(updateRequest.getOza()), User.class);
                return new ShowAUserListResponse(new ListFillerUser(ListType.Requester, user3.getRequesters()), UserCopyCreator.createUser(user3), true);
            case ChatList:
                User user4 = Database.get(Integer.parseInt(updateRequest.getOza()), User.class);
                List<ChatFiller> chatFillers = new LinkedList<>();
                for (Chat chat1 : user4.getMyChats()) {
                    chatFillers.add(new ChatFiller(chat1));
                }
                return new ShowChatListResponse(chatFillers, true);
            case SavedTweets:
                User user5 = Database.get(Integer.parseInt(updateRequest.getOza()), User.class);
                List<TweetFiller> fillers = new LinkedList<>();
                for (Tweet_Comment t : user5.getSavedTweets()) {
                    fillers.add(TweetFillerCreator.createTweet(t, user5));
                }
                return new TweetPageResponse(fillers, TweetPageType.Saved, true);
            case GroupList:
                User user6 = Database.get(Integer.parseInt(updateRequest.getOza()), User.class);
                List<GroupFiller> groupFillers = new LinkedList<>();
                for (GroupChat g : user6.getGroups()) {
                    groupFillers.add(new GroupFiller(g));
                }
                return new ShowGroupsResponse(groupFillers, true);
            case MyTweets:
                User user7 = Database.get(Integer.parseInt(updateRequest.getOza()), User.class);
                List<TweetFiller> tweetFillers = new LinkedList<>();
                for (Tweet_Comment t : user7.getMyTweets()) {
                    tweetFillers.add(TweetFillerCreator.createTweet(t, user7));
                }
                return new TweetPageResponse(tweetFillers, TweetPageType.MyTweets, true);
            case WatchUserPage:
                int showFromId = 0, showToId = 0;
                String data1 = updateRequest.getOza();
                for (int i = 0; i < data1.length(); i++) {
                    if (data1.charAt(i) == ',') {
                        showToId = Integer.parseInt(data1.substring(0, i));
                        showFromId = Integer.parseInt(data1.substring(i+1));
                    }
                }
                User showFrom = Database.get(showFromId, User.class);
                User showTo = Database.get(showToId, User.class);
                Checker checker = new Checker();
                WatchUserPageFiller filler = new WatchUserPageFiller(UserCopyCreator.createUser(showFrom), UserCopyCreator.createUser(showTo), checker.checkContainFollowing(showFrom, showTo)
                        , checker.checkContainFollowing(showTo, showFrom) ,checker.checkContainRequester(showFrom, showTo),
                        checker.checkContainBlacklist(showFrom, showTo), checker.checkContainBlacklist(showTo, showFrom),
                        checker.checkContainMuted(showFrom, showTo));
                return new WatchUserPageResponse(filler, true);
            case Comments:
                int motherId = 0, userId1 = 0;
                String data2 = updateRequest.getOza();
                for (int i = 0; i < data2.length(); i++) {
                    if (data2.charAt(i) == ',') {
                        userId1 = Integer.parseInt(data2.substring(0, i));
                        motherId = Integer.parseInt(data2.substring(i+1));
                    }
                }
                Tweet_Comment mother = Database.get(motherId, Tweet_Comment.class);
                User showTo1 = Database.get(userId1, User.class);
                List<TweetFiller> comments = new LinkedList<>();
                for (Tweet_Comment comment : mother.getComments()) {
                    comments.add(TweetFillerCreator.createTweet(comment, showTo1));
                }
                CommentPageFiller filler1 = new CommentPageFiller(TweetFillerCreator.createTweet(mother, showTo1), comments);
                return new ShowCommentsResponse(filler1, true);
        }
        return null;
    }

    @Override
    public Response visitToken() {
        // XD
        return null;
    }

    @Override
    public Response visitGoToExplorer(GoToExplorerEvent goToExplorerEvent) {
        return explorerPage(goToExplorerEvent.getUserId(), false);
    }

    @Override
    public Response visitShowTimeline(TimelineEvent event) {
        if (event.getStatus() == Status.TimelineLiked) {
            return timelinePage(event.getUserId(), false, true);
        } else {
            return timelinePage(event.getUserId(), false, false);
        }
    }

    private Response timelinePage(int userId, boolean isUpdate, boolean isLiked) {
        User tmp = Database.get(userId, User.class);
        List<TweetFiller> fillers = new LinkedList<>();
        if (isLiked) {
            for (User u : tmp.getFollowings()) {
                for (Tweet_Comment t : u.getLikedTweets()) {
                    if (!t.isComment()) {
                        fillers.add(TweetFillerCreator.createTweet(t, u));
                    }
                }
            }
            return new TweetPageResponse(fillers, TweetPageType.TimelineLiked, isUpdate);
        } else {
            for (User u : tmp.getFollowings()) {
                for (Tweet_Comment t : u.getMyTweets()) {
                    if (!t.isComment()) {
                        fillers.add(TweetFillerCreator.createTweet(t, u));
                    }
                }
            }
            return new TweetPageResponse(fillers, TweetPageType.TimelineFollowing, isUpdate);
        }
    }

    private Response explorerPage(int userId, boolean isUpdate) {
        User tmp = Database.get(userId, User.class);
        List<TweetFiller> tweetFillers;
        Tweet_CommentController controller = new Tweet_CommentController();
        tweetFillers = controller.createTweetsToBeShown(tmp);
        return new TweetPageResponse(tweetFillers, TweetPageType.Explorer, isUpdate);
    }
}