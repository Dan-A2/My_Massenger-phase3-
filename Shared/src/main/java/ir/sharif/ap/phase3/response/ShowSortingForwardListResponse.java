package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.SortingCopy;

import java.util.List;

public class ShowSortingForwardListResponse extends Response{

    private final List<SortingCopy> sortingCopies;
    private final String messageToBeForwarded;

    public ShowSortingForwardListResponse(List<SortingCopy> sortingCopies, String messageToBeForwarded) {
        this.sortingCopies = sortingCopies;
        this.messageToBeForwarded = messageToBeForwarded;
    }

    public List<SortingCopy> getSortingCopies() {
        return sortingCopies;
    }

    public String getMessageToBeForwarded() {
        return messageToBeForwarded;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowSortingForwardList(this);
    }
}