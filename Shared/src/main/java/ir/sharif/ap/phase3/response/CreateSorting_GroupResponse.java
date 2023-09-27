package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.UserCopy;

import java.util.List;

public class CreateSorting_GroupResponse extends Response{

    private final List<UserCopy> followings;
    private final boolean isSorting;

    public CreateSorting_GroupResponse(List<UserCopy> followings, boolean isSorting) {
        this.followings = followings;
        this.isSorting = isSorting;
    }

    public boolean isSorting() {
        return isSorting;
    }

    public List<UserCopy> getFollowings() {
        return followings;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitCreateSorting(this);
    }
}