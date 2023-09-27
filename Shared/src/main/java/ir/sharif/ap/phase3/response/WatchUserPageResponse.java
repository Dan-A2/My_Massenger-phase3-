package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.WatchUserPageFiller;

public class WatchUserPageResponse extends Response {

    private final WatchUserPageFiller filler;
    private final boolean isUpdate;

    public WatchUserPageResponse(WatchUserPageFiller filler, boolean isUpdate) {
        this.filler = filler;
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public WatchUserPageFiller getFiller() {
        return filler;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitWatchPage(this);
    }
}
