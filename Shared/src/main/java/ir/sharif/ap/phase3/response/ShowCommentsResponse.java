package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.CommentPageFiller;

public class ShowCommentsResponse extends Response {

    private final CommentPageFiller filler;
    private final boolean isUpdate;

    public ShowCommentsResponse(CommentPageFiller filler, boolean isUpdate) {
        this.filler = filler;
        this.isUpdate = isUpdate;
    }

    public CommentPageFiller getFiller() {
        return filler;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowComments(this);
    }
}