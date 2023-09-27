package org.openjfx.view.watchUserPage;

import ir.sharif.ap.phase3.event.chat.OpenChatEvent;
import ir.sharif.ap.phase3.event.user.BlockEvent;
import ir.sharif.ap.phase3.event.user.FollowRequestEvent;
import ir.sharif.ap.phase3.event.user.Follow_UnfollowEvent;
import ir.sharif.ap.phase3.event.user.Mute_UnmuteEvent;
import ir.sharif.ap.phase3.model.help.WatchUserPageFiller;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.ImageConvertor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.ImagePattern;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;
import ir.sharif.ap.phase3.util.COMMANDS;
import org.openjfx.view.mainMenu.MainMenu;

public class WatchUserPage {

    private final WatchUserPageFiller filler;
    private final EventListener listener;
    private Scene scene;

    public WatchUserPage(WatchUserPageFiller filler, EventListener listener) {
        this.filler = filler;
        this.listener = listener;
    }

    public void show(){
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Config.getConfig("watchUserPage").getProperty(String.class,"address")));
        try {
            Parent root = loader.load();
            scene = new Scene(root);
            WatchUserPageView view = loader.getController();
            view.getFirstnameLabel().setText(filler.getShowFrom().getFirstName());
            view.getLastnameLabel().setText(filler.getShowFrom().getLastName());
            if (filler.getShowFrom().getLastSeen() != null) {
                if (filler.getShowFrom().getWhoCanSeeLastSeen().equals(Config.getConfig("setting").getProperty(String.class, "nobody"))) {
                    System.out.println("can't see last seen");
                    view.getLastSeenLabel().setText("...");
                } else if (filler.getShowFrom().getWhoCanSeeLastSeen().equals(Config.getConfig("setting").getProperty(String.class, "followers"))) {
                    System.out.println("followers can see");
                    if (filler.isContainFollowing21()) {
                        System.out.println("showing last seen");
                        view.getLastSeenLabel().setText(filler.getShowFrom().getLastSeen());
                        if (filler.getShowFrom().getProfileImage() != null) {
                            ImageConvertor convertor = new ImageConvertor();
                            view.getProfileCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(filler.getShowFrom().getProfileImage()))));
                        }
                    } else {
                        System.out.println("not a follower");
                        view.getLastSeenLabel().setText("...");
                    }
                } else {
                    System.out.println("showing all that shit");
                    view.getLastSeenLabel().setText(filler.getShowFrom().getLastSeen());
                    if (filler.getShowFrom().getProfileImage() != null) {
                        ImageConvertor convertor = new ImageConvertor();
                        view.getProfileCircle().setFill(new ImagePattern(convertor.convertData(convertor.convertString(filler.getShowFrom().getProfileImage()))));
                    }
                }
            }
            if (filler.getShowFrom().isOnline()) {
                view.getOnlineCircle().setVisible(true);
                view.getOfflineCircle().setVisible(false);
            } else {
                view.getOnlineCircle().setVisible(false);
                view.getOfflineCircle().setVisible(true);
            }
            view.getUsernameLabel().setText(filler.getShowFrom().getUsername());
            view.getFollowersNum().setText(Integer.toString(filler.getShowFrom().getFollowerCount()));
            view.getFollowingsNum().setText(Integer.toString(filler.getShowFrom().getFollowingCount()));
            view.getTweetsNum().setText(Integer.toString(filler.getShowFrom().getTweetCount()));
            update(view);
            setListener(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(WatchUserPageView view){
        if (filler.isContainFollowing21()){
            view.getFollowBtn1().setVisible(false);
            view.getUnfollowBtn1().setVisible(true);
        } else if (filler.isContainRequester()) {
            view.getRequestedBtn().setVisible(true);
            view.getFollowBtn1().setVisible(false);
            view.getUnfollowBtn1().setVisible(false);
        } else {
            view.getFollowBtn1().setVisible(true);
            view.getUnfollowBtn1().setVisible(false);
        }
        if (filler.isContainFollowing21() || filler.isContainFollowing12() && !filler.isContainBlackList12() && !filler.isContainBlackList21()) {
            view.getSendMassageBtn().setVisible(true);
        } else {
            view.getSendMassageBtn().setVisible(false);
        }
        if (filler.isContainBlackList21()) {
            view.getBlockBtn().setVisible(false);
            view.getUnblockBtn().setVisible(true);
        } else {
            view.getBlockBtn().setVisible(true);
            view.getUnblockBtn().setVisible(false);
        }
        if(filler.isContainMuted12()) {
            view.getMuteBtn().setVisible(false);
            view.getUnmuteBtn().setVisible(true);
        } else {
            view.getMuteBtn().setVisible(true);
            view.getUnmuteBtn().setVisible(false);
        }
    }

    private void setListener(WatchUserPageView view){
        view.setListener(command -> {
            if (command == COMMANDS.BLOCK) {
                BlockEvent event = new BlockEvent(filler.getShowTo().getId(), filler.getShowFrom().getId(), true);
                listener.listen(event);
            } else if (command == COMMANDS.FOLLOW) {
                if (filler.getShowFrom().isAccountPublic()) {
                    Follow_UnfollowEvent event1 = new Follow_UnfollowEvent(filler.getShowTo().getId(), filler.getShowFrom().getId(), true);
                    listener.listen(event1);
                } else {
                    FollowRequestEvent event = new FollowRequestEvent(filler.getShowFrom().getId(), filler.getShowTo().getId(), true);
                    listener.listen(event);
                    view.getFollowBtn1().setVisible(false);
                    view.getRequestedBtn().setVisible(true);
                }
            } else if (command == COMMANDS.REMOVEREQUEST) {
                FollowRequestEvent event = new FollowRequestEvent(filler.getShowFrom().getId(), filler.getShowTo().getId(), false);
                listener.listen(event);
                view.getRequestedBtn().setVisible(false);
                view.getFollowBtn1().setVisible(true);
            } else if (command == COMMANDS.MOUSECHANGEFOLLOWP) {
                view.getFollowBtn1().setVisible(false);
                view.getFollowBtn2().setVisible(true);
            } else if (command == COMMANDS.MOUSECHANGEFOLLOWN) {
                view.getFollowBtn1().setVisible(true);
                view.getFollowBtn2().setVisible(false);
            } else if (command == COMMANDS.MOUSECHANGEUNFOLLOWP) {
                view.getUnfollowBtn1().setVisible(false);
                view.getUnfollowBtn2().setVisible(true);
            } else if (command == COMMANDS.MOUSECHANGEUNFOLLOWN) {
                view.getUnfollowBtn1().setVisible(true);
                view.getUnfollowBtn2().setVisible(false);
            } else if (command == COMMANDS.MUTE) {
                Mute_UnmuteEvent event2 = new Mute_UnmuteEvent(filler.getShowTo().getId(), filler.getShowFrom().getId(), true);
                listener.listen(event2);
            } else if (command == COMMANDS.UNBLOCK) {
                BlockEvent event3 = new BlockEvent(filler.getShowTo().getId(), filler.getShowFrom().getId(), false);
                listener.listen(event3);
            } else if (command == COMMANDS.UNFOLLOW) {
                Follow_UnfollowEvent event4 = new Follow_UnfollowEvent(filler.getShowTo().getId(), filler.getShowFrom().getId(), false);
                listener.listen(event4);
            } else if (command == COMMANDS.UNMUTE) {
                Mute_UnmuteEvent event5 = new Mute_UnmuteEvent(filler.getShowTo().getId(), filler.getShowFrom().getId(), false);
                listener.listen(event5);
            } else if (command == COMMANDS.SENDMASSAGE) {
                OpenChatEvent event = new OpenChatEvent(filler.getShowTo().getId(), filler.getShowFrom().getId(), MainMenu.getCurrentUser().getId());
                listener.listen(event);
            } else if (command == COMMANDS.BACK) {
                SceneManager.getSceneManager().getBack();
            } else if (command == COMMANDS.MAINMENU) {
                SceneManager.getSceneManager().backToMain();
            }
            update(view);
        });

    }

    public Scene getScene() {
        return scene;
    }
}