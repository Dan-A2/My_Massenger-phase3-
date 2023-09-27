package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class WatchUserPageEvent extends UserEvent {

    private final String showTo;
    private final String showFrom;

    public WatchUserPageEvent(String showTO, String showFrom) {
        this.showTo = showTO;
        this.showFrom = showFrom;
    }

    public String getShowTo() {
        return showTo;
    }

    public String getShowFrom() {
        return showFrom;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitWatchPage(this);
    }
}
