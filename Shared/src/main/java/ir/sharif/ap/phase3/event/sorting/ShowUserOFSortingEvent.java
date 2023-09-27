package ir.sharif.ap.phase3.event.sorting;

import ir.sharif.ap.phase3.response.Response;

public class ShowUserOFSortingEvent extends SortingEvent {

    private final int userId;
    private final String sortingName;

    public ShowUserOFSortingEvent(int userId, String sortingName) {
        this.userId = userId;
        this.sortingName = sortingName;
    }

    public int getUserId() {
        return userId;
    }

    public String getSortingName() {
        return sortingName;
    }

    @Override
    public Response visit(SortingVisitor visitor) {
        return visitor.visitShowUsers(this);
    }
}