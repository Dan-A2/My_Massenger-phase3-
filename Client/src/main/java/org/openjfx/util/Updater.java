package org.openjfx.util;

import ir.sharif.ap.phase3.event.general.UpdateRequest;
import ir.sharif.ap.phase3.util.Loop;
import ir.sharif.ap.phase3.util.Status;
import org.openjfx.connector.EventListener;
import org.openjfx.view.mainMenu.MainMenu;

public class Updater {

    private final EventListener listener;
    private final double fps = 0.5;
    private Loop loop;

    public Updater(EventListener listener) {
        this.listener = listener;
    }

    public void updateExplorer(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.Explorer, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps , runnable);
        loop.start();
    }

    public void updateTimelineLiked(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.TimelineLiked, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateTimelineFollowing(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.TimelineFollowing, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateSavedTweets(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.SavedTweets, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateChat(int chatId) {
        System.out.println("updating chat");
        if (loop != null) {
            System.out.println("stopping loop");
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.Chat,  MainMenu.getCurrentUser().getId() + "," + chatId);
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateChatList(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.ChatList, userId + "");
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateGroupChat(int userId, int groupId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.GroupChat, userId + "," + groupId);
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateGroupList(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.GroupList, userId + "");
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateFollowings(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.Followings, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateFollowers(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.Followers, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateRequesters(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.Requesters, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateMyTweets(int userId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.MyTweets, Integer.toString(userId));
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateComments(int userId, int motherId) {
        if (loop != null) {
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.Comments, userId + "," + motherId);
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void updateWatchPage(int showToId, int showFromId) {
        System.out.println("in update watch page");
        if (loop != null) {
            System.out.println("stopping loop");
            loop.stop();
        }
        Runnable runnable = () -> {
            UpdateRequest request = new UpdateRequest(Status.WatchUserPage, showToId + "," + showFromId);
            listener.listen(request);
        };
        loop = new Loop(fps, runnable);
        loop.start();
    }

    public void stopLoop() {
        if (loop != null) {
            loop.stop();
        }
    }
}