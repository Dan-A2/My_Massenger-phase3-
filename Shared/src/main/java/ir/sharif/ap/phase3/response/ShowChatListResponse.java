package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.ChatFiller;

import java.util.List;

public class ShowChatListResponse extends Response{

    private final List<ChatFiller> chatFillers;
    private final boolean isUpdate;

    public ShowChatListResponse(List<ChatFiller> chatFillers, boolean isUpdate) {
        this.chatFillers = chatFillers;
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public List<ChatFiller> getChatFillers() {
        return chatFillers;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowChatList(this);
    }
}