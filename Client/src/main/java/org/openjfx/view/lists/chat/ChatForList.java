package org.openjfx.view.lists.chat;

import ir.sharif.ap.phase3.event.chat.ViewChatEvent;
import ir.sharif.ap.phase3.model.help.ChatFiller;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.util.Config;
import ir.sharif.ap.phase3.util.COMMANDS;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openjfx.SceneManager;
import org.openjfx.connector.EventListener;

public class ChatForList {

    private AnchorPane pane;
    private final ChatFiller chat;
    private final int unseen;
    private final UserCopy thisUser;
    private final EventListener listener;

    public ChatForList(UserCopy thisUser, ChatFiller chat, int unseen, EventListener listener) {
        this.chat = chat;
        this.unseen = unseen;
        this.thisUser = thisUser;
        this.listener = listener;
    }

    public void show() {
        Config config = Config.getConfig("lists");
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(config.getProperty(String.class, "chatForListAddress")));
        try {
            pane = loader.load();
            ChatForListView view = loader.getController();
            if (chat.getUser1().getUsername().equals(thisUser.getUsername())) {
                view.getChatLabel().setText(chat.getUser2().getUsername());
            } else {
                view.getChatLabel().setText(chat.getUser1().getUsername());
            }
            view.getUnseenChatsLabel().setText(Integer.toString(unseen));
            view.setChatListListener(command -> {
                if (command == COMMANDS.VIEW) {
                    ViewChatEvent event = new ViewChatEvent(thisUser.getId(), chat.getChatId());
                    listener.listen(event);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getPane() {
        return pane;
    }

}