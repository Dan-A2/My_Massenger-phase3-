package ir.sharif.ap.phase3.model.help;

import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.ListType;

import java.util.LinkedList;
import java.util.List;

public class ListFillerUser {

    private final ListType type;
    private final List<UserCopy> users;

    public ListFillerUser(ListType type, List<User> userList) {
        this.type = type;
        users = new LinkedList<>();
        for (User u : userList) {
            users.add(new UserCopy(u));
        }
    }

    public ListType getType() {
        return type;
    }

    public List<UserCopy> getUsers() {
        return users;
    }
}