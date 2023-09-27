package ir.sharif.ap.phase3.event.message;

import ir.sharif.ap.phase3.response.Response;

public interface MessageVisitor {
    Response visitEditMessage(EditMassageEvent event);
    Response visitSendMessage(MassageEvent event);
    Response visitSaveMessage(SaveMassageEvent event);
    Response visitSaveNote(SaveNoteEvent event);
    Response visitShowForwardList(ShowForwardListEvent showForwardListEvent);
    Response visitScheduled(ScheduledMassageEvent event);
}