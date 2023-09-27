package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.SortingCopy;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.model.main.User;

import java.util.LinkedList;
import java.util.List;

public class UserSortingsResponse extends Response{
    
    private final List<SortingCopy> sortings;
    private final UserCopy thisUser;

    public UserSortingsResponse(User user, UserCopy thisUser) {
        this.thisUser = thisUser;
        sortings = new LinkedList<>();
        for (String sotingName : user.getMySortings().keySet()) {
            sortings.add(new SortingCopy(sotingName, user.getMySortings().get(sotingName).getUsers()));
        }
    }

    public List<SortingCopy> getSortings() {
        return sortings;
    }

    public UserCopy getThisUser() {
        return thisUser;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowSorting(this);
    }
}