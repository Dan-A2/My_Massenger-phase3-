package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.GroupFiller;

import java.util.List;

public class ShowGroupsResponse extends Response{

    private final List<GroupFiller> groupFillers;
    private final boolean isUpdate;

    public ShowGroupsResponse(List<GroupFiller> groupFillers, boolean isUpdate) {
        this.groupFillers = groupFillers;
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public List<GroupFiller> getGroupFillers() {
        return groupFillers;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowGroups(this);
    }
}