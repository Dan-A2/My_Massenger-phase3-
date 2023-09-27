package controller.visitors;

import database.Database;
import util.UserCopyCreator;
import ir.sharif.ap.phase3.event.sorting.*;
import ir.sharif.ap.phase3.model.help.ListFillerUser;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.response.NoResponse;
import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.response.ShowAUserListResponse;
import ir.sharif.ap.phase3.util.ListType;
import logic.SendingMassageController;
import logic.UserController;

import java.util.LinkedList;
import java.util.List;

public class SortingHandler implements SortingVisitor {

    private static SortingHandler sortingHandler;

    private SortingHandler() {}

    public static SortingHandler getInstance() {
        if (sortingHandler == null)
            sortingHandler = new SortingHandler();
        return sortingHandler;
    }

    @Override
    public Response visitCreateSorting(CreateSortingEvent createSortingEvent) {
        User user = Database.get(createSortingEvent.getUserId(), User.class);
        List<User> users = new LinkedList<>();
        for (Integer id : createSortingEvent.getUsersId()) {
            User tmp = Database.get(id, User.class);
            users.add(tmp);
        }
        UserController controller = new UserController();
        controller.createSorting(user, createSortingEvent.getSortingName(), users);
        return new NoResponse();
    }

    @Override
    public Response visitDeleteSorting(DeleteSortingEvent deleteSortingEvent) {
        User user = Database.get(deleteSortingEvent.getUserID(), User.class);
        UserController controller = new UserController();
        controller.removeSorting(user, deleteSortingEvent.getSortingName());
        return new NoResponse();
    }

    @Override
    public Response visitForward(ForwardToSortingEvent forwardToSortingEvent) {
        SendingMassageController controller = new SendingMassageController();
        User user = Database.get(forwardToSortingEvent.getForwardFromId(), User.class);
        controller.sendMassageSorting(user, forwardToSortingEvent.getSortingName(), forwardToSortingEvent.getTweet(), forwardToSortingEvent.isForwarded());
        return new NoResponse();
    }

    @Override
    public Response visitShowUsers(ShowUserOFSortingEvent showUserOFSortingEvent) {
        User user = Database.get(showUserOFSortingEvent.getUserId(), User.class);
        return new ShowAUserListResponse(new ListFillerUser(ListType.Users, user.getMySortings().get(showUserOFSortingEvent.getSortingName()).getUsers()), UserCopyCreator.createUser(user), false);
    }
}