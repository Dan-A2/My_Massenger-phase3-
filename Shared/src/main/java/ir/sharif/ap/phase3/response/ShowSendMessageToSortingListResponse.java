package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.SortingCopy;

import java.util.List;

public class ShowSendMessageToSortingListResponse extends Response{

    private final String message;
    private final List<SortingCopy> sortings;

    public ShowSendMessageToSortingListResponse(String message, List<SortingCopy> sortings) {
        this.message = message;
        this.sortings = sortings;
    }

    public String getMessage() {
        return message;
    }

    public List<SortingCopy> getSortings() {
        return sortings;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowSendMessageToSortingList(this);
    }
}