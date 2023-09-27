package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.ChatFiller;

public class ViewChatResponse extends Response {

    private final ChatFiller filler;
    private final boolean isUpdate;

    public ViewChatResponse(ChatFiller filler, boolean isUpdate) {
        this.filler = filler;
        this.isUpdate = isUpdate;
    }

    public ChatFiller getFiller() {
        return filler;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitViewChat(this);
    }
}