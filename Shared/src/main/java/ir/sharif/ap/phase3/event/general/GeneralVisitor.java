package ir.sharif.ap.phase3.event.general;

import ir.sharif.ap.phase3.response.Response;

public interface GeneralVisitor {
    Response visitUpdate(UpdateRequest request);
    Response visitToken();
    Response visitGoToExplorer(GoToExplorerEvent event);
    Response visitShowTimeline(TimelineEvent event);
}