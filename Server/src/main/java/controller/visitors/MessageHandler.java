package controller.visitors;

import database.Database;
import database.scheduledM.MassageSaver;
import ir.sharif.ap.phase3.model.help.ScheduledMessage;
import ir.sharif.ap.phase3.response.*;
import ir.sharif.ap.phase3.util.Feedback;
import util.UserCopyCreator;
import ir.sharif.ap.phase3.event.message.*;
import ir.sharif.ap.phase3.model.help.SortingCopy;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.model.main.Message;
import ir.sharif.ap.phase3.model.main.User;
import logic.MassageController;
import logic.SendingMassageController;
import logic.UserController;

import java.util.LinkedList;
import java.util.List;

public class MessageHandler implements MessageVisitor {

    private static MessageHandler handler;

    private MessageHandler() {
    }

    public static MessageHandler getInstance() {
        if (handler == null)
            handler = new MessageHandler();
        return handler;
    }

    @Override
    public Response visitEditMessage(EditMassageEvent editMassageEvent) {
        MassageController controller = new MassageController();
        Message message = Database.get(editMassageEvent.getMassageId(), Message.class);
        controller.editMassage(message, editMassageEvent.getText());
        return new NoResponse();
    }

    @Override
    public Response visitSendMessage(MassageEvent messageEvent) {
        SendingMassageController controller = new SendingMassageController();
        User from = Database.get(messageEvent.getSenderId(), User.class);
        User to = Database.get(messageEvent.getReceiverId(), User.class);
        controller.sendMassage1(from, to, messageEvent.getMassage(), messageEvent.isForwarded(), messageEvent.getImage());
        return new NoResponse();
    }

    @Override
    public Response visitSaveMessage(SaveMassageEvent saveMassageEvent) {
        UserController controller = new UserController();
        User user = Database.get(saveMassageEvent.getSaverId(), User.class);
        Message message = Database.get(saveMassageEvent.getMassageId(), Message.class);
        controller.saveMassage(user, message);
        return new NoResponse();
    }

    @Override
    public Response visitSaveNote(SaveNoteEvent saveNoteEvent) {
        UserController controller = new UserController();
        User user = Database.get(saveNoteEvent.getUserID(), User.class);
        controller.saveNote(user, saveNoteEvent.getText());
        return new NoResponse();
    }

    @Override
    public Response visitShowForwardList(ShowForwardListEvent showForwardListEvent) {
        User user = Database.get(showForwardListEvent.getForwardFromId(), User.class);
        if (showForwardListEvent.getAns() == 0) { // forward user
            List<UserCopy> followings = new LinkedList<>();
            for (User u : user.getFollowings()) {
                followings.add(UserCopyCreator.createUser(u));
            }
            return new ShowUserForwardListResponse(followings, showForwardListEvent.getMassageToBeForwarded());
        } else { // forward sorting
            List<SortingCopy> sortingCopies = new LinkedList<>();
            for (String sortingName : user.getMySortings().keySet()) {
                sortingCopies.add(new SortingCopy(sortingName, user.getMySortings().get(sortingName).getUsers()));
            }
            return new ShowSortingForwardListResponse(sortingCopies, showForwardListEvent.getMassageToBeForwarded());
        }
    }

    @Override
    public Response visitScheduled(ScheduledMassageEvent scheduledMassageEvent) {
        ScheduledMessage message = new ScheduledMessage(scheduledMassageEvent.getTxt(), scheduledMassageEvent.getHour()
        , scheduledMassageEvent.getMinute(), scheduledMassageEvent.getSenderId(), scheduledMassageEvent.getGroupId());
        MassageSaver.saveMassage(message);
        return new FeedbackResponse(Feedback.ScheduleConfirm);
    }
}