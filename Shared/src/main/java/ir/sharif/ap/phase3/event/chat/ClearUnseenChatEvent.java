package ir.sharif.ap.phase3.event.chat;

import ir.sharif.ap.phase3.response.Response;

public class ClearUnseenChatEvent extends ChatEvent {

    private final int chatId;
    private final int userId;

    public ClearUnseenChatEvent(int chatId, int userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public int getChatId() {
        return chatId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Response visit(ChatVisitor visitor) {
        return visitor.visitClearUnseen(this);
    }
}