package ir.sharif.ap.phase3.event.sorting;

import ir.sharif.ap.phase3.response.Response;

public interface SortingVisitor {
    Response visitCreateSorting(CreateSortingEvent event);
    Response visitDeleteSorting(DeleteSortingEvent event);
    Response visitForward(ForwardToSortingEvent event);
    Response visitShowUsers(ShowUserOFSortingEvent event);
}