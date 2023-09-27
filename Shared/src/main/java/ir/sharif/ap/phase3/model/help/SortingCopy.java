package ir.sharif.ap.phase3.model.help;

import ir.sharif.ap.phase3.model.main.User;

import java.util.LinkedList;
import java.util.List;

public class SortingCopy {

    private final String sortingName;
    private final List<UserCopy> users = new LinkedList<>();

    public SortingCopy(String sortingName, List<User> users) {
        this.sortingName = sortingName;
        for (User u : users) {
            this.users.add(new UserCopy(u));
        }
    }

    public String getSortingName() {
        return sortingName;
    }

    public List<UserCopy> getUsers() {
        return users;
    }
}