package ir.sharif.ap.phase3.event.chat;

import ir.sharif.ap.phase3.response.Response;

public class ViewChatEvent extends ChatEvent {

    private final int userId;
    private final int chatId;

    public ViewChatEvent(int userId, int chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public int getUserId() {
        return userId;
    }

    public int getChatId() {
        return chatId;
    }

    @Override
    public Response visit(ChatVisitor visitor) {
        return visitor.visitViewChat(this);
    }
}