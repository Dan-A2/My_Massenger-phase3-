package ir.sharif.ap.phase3.event.chat;

import ir.sharif.ap.phase3.response.Response;

public class OpenChatEvent extends ChatEvent {

    private final int user1Id;
    private final int user2Id;
    private final int requesterId;

    public OpenChatEvent(int user1Id, int user2Id, int requesterId) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.requesterId = requesterId;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public int getRequesterId() {
        return requesterId;
    }

    @Override
    public Response visit(ChatVisitor visitor) {
        return visitor.visitOpenChat(this);
    }
}