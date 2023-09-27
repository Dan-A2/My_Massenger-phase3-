package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.GroupFiller;

public class ShowGroupResponse extends Response{

    private final GroupFiller filler;
    private final boolean isUpdate;

    public ShowGroupResponse(GroupFiller filler, boolean isUpdate) {
        this.filler = filler;
        this.isUpdate = isUpdate;
    }

    public GroupFiller getFiller() {
        return filler;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowGroup(this);
    }
}