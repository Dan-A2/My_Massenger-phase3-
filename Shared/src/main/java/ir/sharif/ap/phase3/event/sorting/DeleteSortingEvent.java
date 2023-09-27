package ir.sharif.ap.phase3.event.sorting;

import ir.sharif.ap.phase3.response.Response;

public class DeleteSortingEvent extends SortingEvent {

    private final int userID;
    private final String sortingName;

    public DeleteSortingEvent(int userID, String sortingName) {
        this.userID = userID;
        this.sortingName = sortingName;
    }

    public int getUserID() {
        return userID;
    }

    public String getSortingName() {
        return sortingName;
    }

    @Override
    public Response visit(SortingVisitor visitor) {
        return visitor.visitDeleteSorting(this);
    }
}