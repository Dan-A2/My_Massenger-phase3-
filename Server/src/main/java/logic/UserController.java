package logic;

import database.Database;
import ir.sharif.ap.phase3.model.main.*;
import ir.sharif.ap.phase3.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.LinkedList;
import java.util.List;

public class UserController {

    static private final Logger logger = (Logger) LogManager.getLogger(UserController.class);

    public User find(String username){
        return Database.getUser(username);
    }

    public void blockUser(User user1, User user2){
        if (user1.getId() != user2.getId()) {
            for (User u : user1.getBlackList()) {
                if (u.getId() == user2.getId()) {
                    return;
                }
            }
            logger.info("User " + user1.getId() + " has blocked User " + user2.getUsername());
            user1.getBlackList().add(user2);
        }
        Database.update(user1);
    }

    public void unblockUser(User user1, User user2){
        logger.info("User " + user1.getId() + " has unblocked User " + user2.getUsername());
        for (User u : user1.getBlackList()) {
            if (u.getId() == user2.getId()) {
                user1.getBlackList().remove(u);
                break;
            }
        }
        Database.update(user1);
    }

    public void like(User user, Tweet_Comment tweet_comment){
        NotificationHandler handler = new NotificationHandler();
        logger.info("User " + user.getUsername() + " has liked Tweet " + tweet_comment.getID());
        handler.sendNotif(user.getUsername() + Config.getConfig("tweet").getProperty(String.class, "likeTweetNotif"), tweet_comment.getSender(), user);
        for (Tweet_Comment t : user.getLikedTweets()) {
            if (t.getID() == tweet_comment.getID()) {
                return;
            }
        }
        user.getLikedTweets().add(tweet_comment);
        tweet_comment.getLikers().add(user);
        Database.update(tweet_comment);
        Database.update(user);
    }

    public void dislike(User user, Tweet_Comment tweet_comment){
        NotificationHandler handler = new NotificationHandler();
        logger.info("User " + user.getUsername() + " has disliked Tweet " + tweet_comment.getID());
        handler.sendNotif(user.getUsername() + Config.getConfig("tweet").getProperty(String.class, "dislikeTweetNotif"), tweet_comment.getSender(), user);
        for (Tweet_Comment t : user.getLikedTweets()) {
            if (t.getID() == tweet_comment.getID()) {
                user.getLikedTweets().remove(t);
                break;
            }
        }
        for (User u : tweet_comment.getLikers()) {
            if (u.getId() == user.getId()) {
                tweet_comment.getLikers().remove(u);
                break;
            }
        }
        Database.update(tweet_comment);
        Database.update(user);
    }

    public void mute(User muter, User user2){
        if (muter.getId() != user2.getId()) {
            muter.getMuted().add(user2);
            logger.info("User " + muter.getUsername() + " has muted " + user2.getUsername());
        }
        Database.update(muter);
        Database.update(user2);
    }

    public void unMute(User muter, User user2){
        for (User u : muter.getMuted()) {
            if (u.getId() == user2.getId()) {
                muter.getMuted().remove(u);
                break;
            }
        }
        logger.info("User " + muter.getUsername() + " has unmuted " + user2.getUsername());
        Database.update(muter);
    }

    public void saveTweet(User saver, Tweet_Comment tweet_comment){
        for (Tweet_Comment t : saver.getSavedTweets()) {
            if (t.getID() == tweet_comment.getID()) {
                return;
            }
        }
        saver.getSavedTweets().add(tweet_comment);
        tweet_comment.getSavers().add(saver);
        logger.info("User" + saver.getUsername() + " has saved Tweet " + tweet_comment.getID());
        Database.update(tweet_comment);
        Database.update(saver);
    }

    public void changeBirthday(User user, String birthday){
        user.setBirthday(birthday);
        logger.info("User " + user.getUsername() + " has changed his/her birthday");
        System.out.println("after birthday change");
        Database.update(user);
    }

    public void changeFirstName(User user, String name){
        user.setFirstName(name);
        logger.info("User " + user.getUsername() + " has changed his/her firstname");
        Database.update(user);
    }

    public void changeLastName(User user, String name){
        user.setLastName(name);
        logger.info("User " + user.getUsername() + " has changed his/her lastname");
        Database.update(user);
    }

    public void changeUsername(User user, String username){
        logger.info("User " + user.getUsername() + " has changed his/her username to " + username);
        user.setUsername(username);
        Database.update(user);
    }

    public void changePassword(User user, String password){
        user.setPassword(password);
        logger.info("User " + user.getUsername() + " has changed his/her password");
        Database.update(user);
    }

    public void changeEmail(User user, String email){
        user.setEmail(email);
        logger.info("User " + user.getUsername() + " has changed his/her email");
        System.out.println("after email change");
        Database.update(user);
    }

    public void changePhoneNumber(User user, String phonenumber){
        user.setPhoneNumber(phonenumber);
        logger.info("User " + user.getUsername() + " has changed his/her phonenumber");
        Database.update(user);;
    }

    public void changeBio(User user, String bio){
        user.setBio(bio);
        logger.info("User " + user.getUsername() + " has changed his/her bio");
        Database.update(user);;
    }

    public void follow(User user1, User user2){
        if (user1.getId() != user2.getId()) {
            NotificationHandler handler = new NotificationHandler();
            handler.sendNotif(user1.getUsername() + " " + Config.getConfig("watchUserPage").getProperty(String.class, "followNotification"), user2, user1);
            user1.getFollowings().add(user2);
            user2.getFollowers().add(user1);
            logger.info("User " + user1.getUsername() + " has followed User " + user2.getUsername());
            Database.update(user1);
            Database.update(user2);
        }
    }

    public void unfollow(User user1, User user2){
        for (User u : user1.getFollowings()) {
            if (u.getId() == user2.getId()) {
                user1.getFollowings().remove(u);
                break;
            }
        }
        for (User u : user2.getFollowers()) {
            if (u.getId() == user1.getId()) {
                user2.getFollowers().remove(u);
                break;
            }
        }
        logger.info("User " + user1.getUsername() + " has followed User " + user2.getUsername());
        Database.update(user1);
        Database.update(user2);
    }

    public void unSaveTweet(User saver, Tweet_Comment tweet) {
        for (Tweet_Comment t : saver.getSavedTweets()) {
            if (t.getID() == tweet.getID()) {
                saver.getSavedTweets().remove(t);
                break;
            }
        }
        for (User u : tweet.getSavers()) {
            if (u.getId() == saver.getId()) {
                tweet.getSavers().remove(u);
                break;
            }
        }
        logger.info("User" + saver.getUsername() + " has unsaved Tweet " + tweet.getID());
        Database.update(tweet);
        Database.update(saver);
    }

    public void createSorting(User user, String sortingName, List<User> id){
        UserList tmp = new UserList(id);
        user.getMySortings().put(sortingName, tmp);
        logger.info("User " + user.getUsername() + " has created a sorting named " + sortingName);
        Database.save(tmp);
        Database.update(user);
    }

    public void removeSorting(User user, String sortingName){
        UserList tmp = user.getMySortings().get(sortingName);
        tmp.getUsers().clear();
        user.getMySortings().remove(sortingName);
        logger.info("User " + user.getUsername() + " has removed a sorting named " + sortingName);
        Database.update(user);
        Database.delete(tmp);
    }

    public void deleteUser(User user) {
        for (int i = 0; i < user.getFollowers().size(); i++) {
            User tmp = user.getFollowers().get(i);
            unfollow(tmp, user);
        }
        for (int i = 0; i < user.getFollowings().size(); i++) {
            User tmp = user.getFollowings().get(i);
            unfollow(user, tmp);
        }
        for (int i = 0; i < user.getBlackList().size(); i++) {
            User tmp = user.getBlackList().get(i);
            unblockUser(user, tmp);
        }
        for (int i = 0; i < user.getFriends().size(); i++) {
            user.getFriends().remove(i);
            i--;
        }
        for (int i = 0; i < user.getMuted().size(); i++) {
            user.getMuted().remove(i);
            i--;
        }
        RequestController controller2 = new RequestController();
        for (int i = 0; i < user.getRequesters().size(); i++) {
            controller2.ignoreRequest(user, user.getRequesters().get(i));
            i--;
        }
        Tweet_CommentController controller = new Tweet_CommentController();
        for (int i = 0; i < user.getMyTweets().size(); i++) {
            Tweet_Comment tmp = user.getMyTweets().get(i);
            controller.deleteTweet(tmp);
        }
        for (int i = 0; i < user.getLikedTweets().size(); i++) {
            Tweet_Comment tmp = user.getLikedTweets().get(i);
            controller.dislike(tmp);
            dislike(user, tmp);
        }
        for (int i = 0; i < user.getSavedTweets().size(); i++) {
            unSaveTweet(user, user.getSavedTweets().get(i));
        }
        for (int i = 0; i < user.getSavedMassages().size(); i++) {
            unsaveMassage(user, user.getSavedMassages().get(i));
        }
        List<String> stringList = new LinkedList<>(user.getMySortings().keySet());
        for (int i = 0; i < stringList.size(); i++) {
            removeSorting(user, stringList.get(i));
        }
        GroupController controller1 = new GroupController();
        for (int i = 0; i < user.getGroups().size(); i++) {
            for (int j = 0; j < user.getGroups().get(i).getMessages().size(); j++) {
                if (user.getGroups().get(i).getMessages().get(j).getSender().getId() == user.getId()) {
                    Message m = user.getGroups().get(i).getMessages().remove(j);
                    for (int k = 0; k < user.getGroups().get(i).getUsers().size(); k++) {
                        unsaveMassage(user.getGroups().get(i).getUsers().get(k), m);
                    }
                    Database.update(user.getGroups().get(i));
                    Database.delete(m);
                    j--;
                }
            }
            controller1.leavingUser(user, user.getGroups().get(i), false);
        }
        for (int i = 0; i < user.getLeftGroups().size(); i++) {
            for (int j = 0; j < user.getLeftGroups().get(i).getMessages().size(); j++) {
                if (user.getLeftGroups().get(i).getMessages().get(j).getSender().getId() == user.getId()) {
                    Message m = user.getLeftGroups().get(i).getMessages().remove(j);
                    for (int k = 0; k < user.getLeftGroups().get(i).getUsers().size(); k++) {
                        unsaveMassage(user.getLeftGroups().get(i).getUsers().get(k), m);
                    }
                    Database.update(user.getLeftGroups().get(i));
                    Database.delete(m);
                    j--;
                }
            }
        }
        for (User u: Database.allUsers()) {
            for (User user1 : u.getRequesters()) {
                if (user1.getId() == user.getId()) {
                    u.getRequesters().remove(user1);
                    Database.update(u);
                    break;
                }
            }
            for (int i = 0; i < u.getSavedMassages().size(); i++) {
                if (u.getSavedMassages().get(i).getSender().getId() == user.getId()) {
                    u.getSavedMassages().remove(i);
                    Database.update(u);
                    i--;
                }
            }
        }
        ChatController controller3 = new ChatController();
        for (int i = 0; i < user.getMyChats().size(); i++) {
            controller3.deleteChat(user.getMyChats().get(i));
            i--;
        }
        for (int i = 0; i < user.getSavedTextsOfMe().size(); i++) {
            Message m = user.getSavedTextsOfMe().get(i);
            unsaveMassage(user, m);
            Database.delete(m);
            i--;
        }
        user.getUnseenMassagesGroups().clear();
        user.getMyNotifs().clear();
        Database.update(user);
        Database.delete(user);
        logger.info("User " + user.getId() + " has deleted his account");
    }

    public void clearNotifications(User user) {
        user.getMyNotifs().clear();
        Database.update(user);
        logger.info("notifications of User " + user.getId() + " are cleared");
    }

    public void addUserToGroup(User user, GroupChat groupChat) {
        NotificationHandler handler = new NotificationHandler();
        handler.sendNotif(Config.getConfig("chat").getProperty(String.class,"addedToGroupMassage") + groupChat.getGroupName(), user, null);
        user.getGroups().add(groupChat);
        logger.info("User " + user.getUsername() + " is added to the Group " + groupChat.getId());
        user.getUnseenMassagesGroups().put(groupChat.getId(), 0);
        Database.update(user);
    }

    public void saveMassage(User user, Message massage) {
        for (Message m : user.getSavedMassages()) {
            if (m.getID() == massage.getID()) {
                return;
            }
        }
        logger.info("User " + user.getUsername() + " has saved Massage " + massage.getID());
        user.getSavedMassages().add(massage);
        Database.update(user);
    }

    public void unsaveMassage(User user, Message massage){
        for (Message m : user.getSavedMassages()) {
            if (m.getID() == massage.getID()) {
                user.getSavedMassages().remove(m);
                break;
            }
        }
        for (Message m : user.getSavedTextsOfMe()) {
            if (m.getID() == massage.getID()) {
                user.getSavedTextsOfMe().remove(m);
                break;
            }
        }
        logger.info("User " + user.getUsername() + " has unsaved Massage " + massage.getID());
        Database.update(user);
    }

    public void addFollowRequest(User user, User requester) {
        NotificationHandler handler = new NotificationHandler();
        user.getRequesters().add(requester);
        logger.info("User " + requester.getUsername() + " has requested to follow " + user.getUsername());
        handler.sendNotif(Config.getConfig("mainmenu").getProperty(String.class, "addFollowRequest"), user, requester);
        Database.update(user);
    }

    public void removeFollowRequest(User user, User requester) {
        for (User u : user.getRequesters()) {
            if (u.getId() == requester.getId()) {
                user.getRequesters().remove(u);
                break;
            }
        }
        logger.info("User " + requester.getUsername() + " got back the request to follow " + user.getUsername());
        Database.update(user);
    }

    public void setLastSeen(User user) {
        user.setLastSeen();
        Database.update(user);
    }

    public void changeSetting(User user, boolean accountPublicity, String whoCanSeeLastSeen, boolean accountActivity) {
        System.out.println("account public " + accountPublicity);
        user.setAccountPublic(accountPublicity);
        user.setWhoCanSeeLastSeen(whoCanSeeLastSeen);
        user.setActive(accountActivity);
        logger.info("settings changed successfully for User " + user.getUsername());
        Database.update(user);
        System.out.println(Database.get(user.getId(), User.class));
    }

    public void saveNote(User user, String text) {
        Message massage = new Message(text, user);
        Database.save(massage);
        user.getSavedTextsOfMe().add(massage);
        Database.update(user);
    }

    public void changeProfile(User user, String firstname, String lastname, String username, String password, String email, String bio, String phoneNumber, String birthday) {
        if(!user.getFirstName().equals(firstname)) {
            changeFirstName(user, firstname);
        }
        if(!user.getLastName().equals(lastname)) {
            changeLastName(user, lastname);
        }
        changeUsername(user, username);
        if(!user.getPassword().equals(password)) {
            changePassword(user, password);
        }
        if(!user.getEmail().equals(email)){
            changeEmail(user, email);
        }
        if(!user.getPhoneNumber().equals(phoneNumber)){
            changePhoneNumber(user, phoneNumber);
        }
        if(bio != null){
            changeBio(user, bio);
        }
        if(birthday != null){
            changeBirthday(user, birthday);
        }
        logger.info("User " + user.getUsername() + " has edited his/her profile");
        System.out.println("after profile change");
        System.out.println(Database.get(user.getId(), User.class));
    }
}