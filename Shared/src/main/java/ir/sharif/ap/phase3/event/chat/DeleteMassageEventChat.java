package ir.sharif.ap.phase3.event.chat;

import ir.sharif.ap.phase3.response.Response;

public class DeleteMassageEventChat extends ChatEvent {

    private final int massageId;
    private final int userId;
    private final int chatId;

    public DeleteMassageEventChat(int massageId, int userId, int chatId) {
        this.massageId = massageId;
        this.userId = userId;
        this.chatId = chatId;
    }

    public int getMassageId() {
        return massageId;
    }

    public int getUserId() {
        return userId;
    }

    public int getChatId() {
        return chatId;
    }

    @Override
    public Response visit(ChatVisitor visitor) {
        return visitor.visitDeleteMessage(this);
    }
}