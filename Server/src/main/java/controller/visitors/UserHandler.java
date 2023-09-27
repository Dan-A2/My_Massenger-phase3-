package controller.visitors;

import database.Database;
import database.image.ImageSaver;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.model.main.Notification;
import ir.sharif.ap.phase3.model.main.Tweet_Comment;
import ir.sharif.ap.phase3.util.*;
import util.TweetFillerCreator;
import util.UserCopyCreator;
import ir.sharif.ap.phase3.event.user.*;
import ir.sharif.ap.phase3.model.help.ListFillerUser;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.model.help.WatchUserPageFiller;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.response.*;
import logic.RequestController;
import logic.UserController;

import java.util.LinkedList;
import java.util.List;

public class UserHandler implements UserVisitor {

    private static UserHandler handler;

    private UserHandler() {}

    public static UserHandler getInstance() {
        if (handler == null)
            handler = new UserHandler();
        return handler;
    }

    @Override
    public Response visitBlock(BlockEvent blockEvent) {
        UserController controller = new UserController();
        User user1 = Database.get(blockEvent.getUser1Id(), User.class);
        User user2 = Database.get(blockEvent.getUser2Id(), User.class);
        if (blockEvent.isBlock()) {
            controller.blockUser(user1, user2);
        } else {
            controller.unblockUser(user1, user2);
        }
        return new NoResponse();
    }

    @Override
    public Response visitChangeSetting(ChangeSettingsEvent changeSettingsEvent) {
        System.out.println("changing setting");
        UserController controller = new UserController();
        User user = Database.get(changeSettingsEvent.getUserId(), User.class);
        controller.changeSetting(user, changeSettingsEvent.isAccountPublicity(), changeSettingsEvent.getWhoCanSeeLastSeen(), changeSettingsEvent.isAccountActivity());
        return new SettingChangeResponse(UserCopyCreator.createUser(Database.get(user.getId(), User.class)));
    }

    @Override
    public Response visitDeleteAcc(DeleteUserEvent deleteUserEvent) {
        User user = Database.get(deleteUserEvent.getUserId(), User.class);
        UserController controller = new UserController();
        controller.deleteUser(user);
        return new NoResponse();
    }

    @Override
    public Response visitRequest(DoRequestEvent doRequestEvent) {
        RequestController controller = new RequestController();
        User doer = Database.get(doRequestEvent.getDoerId(), User.class);
        User requester = Database.get(doRequestEvent.getRequesterId(), User.class);
        switch (doRequestEvent.getCommand()) {
            case ACCEPT:
                controller.acceptRequest(doer, requester);
                break;
            case IGNORE:
                controller.ignoreRequest(doer, requester);
                break;
            case DENY:
                controller.denyRequest(doer, requester);
                break;
        }
        return new NoResponse();
    }

    @Override
    public Response visitFollowOrUnfollow(Follow_UnfollowEvent follow_unfollowEvent) {
        UserController controller = new UserController();
        User u1 = Database.get(follow_unfollowEvent.getUser1Id(), User.class);
        User u2 = Database.get(follow_unfollowEvent.getUser2Id(), User.class);
        if (follow_unfollowEvent.isFollow()) {
            controller.follow(u1, u2);
        } else {
            controller.unfollow(u1, u2);
        }
        return new NoResponse();
    }

    @Override
    public Response visitFollowOrUnfollowRequest(FollowRequestEvent followRequestEvent) {
        UserController controller = new UserController();
        User requester = Database.get(followRequestEvent.getRequesterId(), User.class);
        User user = Database.get(followRequestEvent.getUserId(), User.class);
        if (followRequestEvent.isAdd()) {
            controller.addFollowRequest(user, requester);
        } else {
            controller.removeFollowRequest(user, requester);
        }
        return new NoResponse();
    }

    @Override
    public Response visitMuteOrUnmute(Mute_UnmuteEvent mute_unmuteEvent) {
        UserController controller = new UserController();
        User doer = Database.get(mute_unmuteEvent.getDoerId(), User.class);
        User user2 = Database.get(mute_unmuteEvent.getUser2Id(), User.class);
        if (mute_unmuteEvent.isMute()) {
            controller.mute(doer, user2);
        } else {
            controller.unMute(doer, user2);
        }
        return new NoResponse();
    }

    @Override
    public Response visitEditProfile(ProfileEditEvent profileEditEvent) {
        UserController controller = new UserController();
        User user = Database.get(profileEditEvent.getUserId(), User.class);
        if (profileEditEvent.getImage() != null) {
            ImageSaver saver = new ImageSaver();
            ImageConvertor convertor = new ImageConvertor();
            user.setProfileImageId(saver.saveImage(convertor.convertData(profileEditEvent.getImage())));
        }
        controller.changeProfile(user, profileEditEvent.getFirstname(), profileEditEvent.getLastname(), profileEditEvent.getUsername(), profileEditEvent.getPassword(), profileEditEvent.getEmail(), profileEditEvent.getBio(), profileEditEvent.getPhoneNumber(), profileEditEvent.getBirthday());
        UserCopy userCopy = UserCopyCreator.createUser(Database.get(user.getId(), User.class));
        return new SettingChangeResponse(userCopy);
    }

    @Override
    public Response visitWatchPage(WatchUserPageEvent watchUserPageEvent) {
        User showFrom = Database.getUser(watchUserPageEvent.getShowFrom());
        if (showFrom == null) {
            return new FeedbackResponse(Feedback.WrongUsername);
        }
        User showTo = Database.getUser(watchUserPageEvent.getShowTo());
        Checker checker = new Checker();
        WatchUserPageFiller filler = new WatchUserPageFiller(UserCopyCreator.createUser(showFrom), UserCopyCreator.createUser(showTo), checker.checkContainFollowing(showFrom, showTo)
        , checker.checkContainFollowing(showTo, showFrom) ,checker.checkContainRequester(showFrom, showTo),
                checker.checkContainBlacklist(showFrom, showTo), checker.checkContainBlacklist(showTo, showFrom),
                checker.checkContainMuted(showFrom, showTo));
        return new WatchUserPageResponse(filler, false);
    }

    @Override
    public Response visitUnsaveMessage(UnsaveMessageEvent unsaveMessageEvent) {
        UserController controller = new UserController();
        User user = Database.get(unsaveMessageEvent.getUserId(), User.class);
        Message message = Database.get(unsaveMessageEvent.getMessageID(), Message.class);
        controller.unsaveMassage(user, message);
        return new NoResponse();
    }

    @Override
    public Response visitShowList(ShowAListEvent showAListEvent) {
        User user = Database.get(showAListEvent.getUserId(), User.class);
        switch (showAListEvent.getType()) {
            case Blocked:
                return new ShowAUserListResponse(new ListFillerUser(ListType.Blocked, user.getBlackList()), UserCopyCreator.createUser(user), false);
            case Followers:
                return new ShowAUserListResponse(new ListFillerUser(ListType.Followers, user.getFollowers()), UserCopyCreator.createUser(user), false);
            case Followings:
                return new ShowAUserListResponse(new ListFillerUser(ListType.Followings, user.getFollowings()), UserCopyCreator.createUser(user), false);
            case Sorting:
                return new UserSortingsResponse(user, UserCopyCreator.createUser(user));
            case ShowMyTweets:
                List<TweetFiller> tweetFillerList = new LinkedList<>();
                for (Tweet_Comment t : user.getMyTweets()) {
                    tweetFillerList.add(TweetFillerCreator.createTweet(t, user));
                }
                return new TweetPageResponse(tweetFillerList, TweetPageType.MyTweets, false);
            case Notification:
                UserController controller = new UserController();
                List<Notification> notifications = new LinkedList<>(user.getMyNotifs());
                controller.clearNotifications(user);
                return new NotificationsResponse(notifications);
            case Requester:
                return new ShowAUserListResponse(new ListFillerUser(ListType.Requester, user.getRequesters()), UserCopyCreator.createUser(user), false);
        }
        return null;
    }

    @Override
    public Response visitDeleteNote(DeleteNoteEvent deleteNoteEvent) {
        User user = Database.get(deleteNoteEvent.getUserId(), User.class);
        Message message = Database.get(deleteNoteEvent.getNoteId(), Message.class);
        UserController controller = new UserController();
        controller.unsaveMassage(user, message);
        Database.delete(message);
        return new NoResponse();
    }
}