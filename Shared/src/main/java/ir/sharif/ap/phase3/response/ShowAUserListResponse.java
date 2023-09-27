package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.ListFillerUser;
import ir.sharif.ap.phase3.model.help.UserCopy;

public class ShowAUserListResponse extends Response{

    private final ListFillerUser listFillerUser;
    private final UserCopy showTo;
    private final boolean isUpdate;

    public ShowAUserListResponse(ListFillerUser listFillerUser, UserCopy showTo, boolean isUpdate) {
        this.listFillerUser = listFillerUser;
        this.showTo = showTo;
        this.isUpdate = isUpdate;
    }

    public ListFillerUser getListFillerUser() {
        return listFillerUser;
    }

    public UserCopy getShowTo() {
        return showTo;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitUserList(this);
    }
}