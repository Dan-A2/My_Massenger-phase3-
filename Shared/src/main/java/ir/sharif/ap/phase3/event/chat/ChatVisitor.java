package ir.sharif.ap.phase3.event.chat;

import ir.sharif.ap.phase3.response.Response;

public interface ChatVisitor {
    Response visitClearUnseen(ClearUnseenChatEvent event);
    Response visitDeleteMessage(DeleteMassageEventChat eventChat);
    Response visitOpenChat(OpenChatEvent event);
    Response visitViewChat(ViewChatEvent event);
}