package ir.sharif.ap.phase3.event.sorting;

import ir.sharif.ap.phase3.response.Response;

import java.util.List;

public class CreateSortingEvent extends SortingEvent {

    private final List<Integer> usersId;
    private final String sortingName;
    private final int userId;

    public CreateSortingEvent(List<Integer> usersId, String sortingName, int userId) {
        this.usersId = usersId;
        this.sortingName = sortingName;
        this.userId = userId;
    }

    public List<Integer> getUsersId() {
        return usersId;
    }

    public int getUserId() {
        return userId;
    }

    public String getSortingName() {
        return sortingName;
    }

    @Override
    public Response visit(SortingVisitor visitor) {
        return visitor.visitCreateSorting(this);
    }
}