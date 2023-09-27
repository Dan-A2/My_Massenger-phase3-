package ir.sharif.ap.phase3.util;

import ir.sharif.ap.phase3.model.main.Tweet_Comment;
import ir.sharif.ap.phase3.model.main.User;

public class Checker {

    private static Checker checker;

    public static Checker getChecker() {
        if (checker == null)
            checker = new Checker();
        return checker;
    }

    public boolean checkContainLike(User user, Tweet_Comment tweet_comment) {
        for (Tweet_Comment tweet : user.getLikedTweets()) {
            if (tweet.getID() == tweet_comment.getID()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkContainSave(User user, Tweet_Comment tweet_comment) {
        for (Tweet_Comment tweet : user.getSavedTweets()) {
            if (tweet.getID() == tweet_comment.getID()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkContainFollowing(User u1, User u2) {
        for (User u : u1.getFollowings()) {
            if (u.getId() == u2.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkContainRequester(User u1, User u2) {
        for (User u : u1.getRequesters()) {
            if (u.getId() == u2.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkContainMuted(User u1, User u2) {
        for (User u : u1.getMuted()) {
            if (u.getId() == u2.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkContainBlacklist(User u1, User u2) {
        for (User u : u1.getBlackList()) {
            if (u.getId() == u2.getId()) {
                return true;
            }
        }
        return false;
    }

}