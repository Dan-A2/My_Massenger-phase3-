package ir.sharif.ap.phase3.event.sorting;

import ir.sharif.ap.phase3.response.Response;

public class ForwardToSortingEvent extends SortingEvent {

    private final String sortingName;
    private final int forwardFromId;
    private final String tweet;
    private final boolean isForwarded;

    public ForwardToSortingEvent(String sortingName, int forwardFromId, String tweet, boolean isForwarded) {
        this.sortingName = sortingName;
        this.forwardFromId = forwardFromId;
        this.tweet = tweet;
        this.isForwarded = isForwarded;
    }

    public String getSortingName() {
        return sortingName;
    }

    public String getTweet() {
        return tweet;
    }

    public boolean isForwarded() {
        return isForwarded;
    }

    public int getForwardFromId() {
        return forwardFromId;
    }

    @Override
    public Response visit(SortingVisitor visitor) {
        return visitor.visitForward(this);
    }
}