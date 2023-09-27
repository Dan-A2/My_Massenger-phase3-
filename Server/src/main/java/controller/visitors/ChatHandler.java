package controller.visitors;

import database.Database;
import ir.sharif.ap.phase3.event.chat.*;
import ir.sharif.ap.phase3.model.help.ChatFiller;
import ir.sharif.ap.phase3.model.main.Chat;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.response.NoResponse;
import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.response.ViewChatResponse;
import logic.ChatController;
import logic.MassageController;

public class ChatHandler implements ChatVisitor {

    private static ChatHandler chatHandler;

    private ChatHandler() {
    }

    public static ChatHandler getChatHandler() {
        if (chatHandler == null)
            chatHandler = new ChatHandler();
        return chatHandler;
    }

    @Override
    public Response visitClearUnseen(ClearUnseenChatEvent clearUnseenChatEvent) {
        Chat chat = Database.get(clearUnseenChatEvent.getChatId(), Chat.class);
        User user = Database.get(clearUnseenChatEvent.getUserId(), User.class);
        if (chat.getUser1().getId() == user.getId()) {
            chat.setUser1UnseenMassages(0);
        } else {
            chat.setUser2UnseenMassages(0);
        }
        Database.update(chat);
        return new NoResponse();
    }

    @Override
    public Response visitDeleteMessage(DeleteMassageEventChat deleteMassageEventChat) {
        Message massage = Database.get(deleteMassageEventChat.getMassageId(), Message.class);
        Chat chat = Database.get(deleteMassageEventChat.getChatId(), Chat.class);
        MassageController controller = new MassageController();
        controller.deleteMassageForChat(massage, chat);
        return new NoResponse();
    }

    @Override
    public Response visitOpenChat(OpenChatEvent openChatEvent) {
        User user1 = Database.get(openChatEvent.getUser1Id(), User.class);
        User user2 = Database.get(openChatEvent.getUser2Id(), User.class);
        ChatController controller = new ChatController();
        Chat tmp = controller.findChat(user1, user2);
        if (user1.getId() == openChatEvent.getRequesterId()) {
            controller.resetMassages(tmp, user1);
        } else {
            controller.resetMassages(tmp, user2);
        }
        return new ViewChatResponse(new ChatFiller(controller.findChat(user1, user2)), false);
    }

    @Override
    public Response visitViewChat(ViewChatEvent viewChatEvent) {
        User user = Database.get(viewChatEvent.getUserId(), User.class);
        Chat chat = Database.get(viewChatEvent.getChatId(), Chat.class);
        ChatController controller = new ChatController();
        controller.resetMassages(chat, user);
        return new ViewChatResponse(new ChatFiller(chat), false);
    }
}