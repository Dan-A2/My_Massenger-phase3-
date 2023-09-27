package ir.sharif.ap.phase3.event.general;

import ir.sharif.ap.phase3.response.Response;

public class GoToExplorerEvent extends GeneralEvent {

    private final int userId;

    public GoToExplorerEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Response visit(GeneralVisitor visitor) {
        return visitor.visitGoToExplorer(this);
    }
}