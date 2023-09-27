package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.UserCopy;

import java.util.List;

public class ShowUserForwardListResponse extends Response{

    private final List<UserCopy> followings;
    private final String messageToBeForwarded;

    public ShowUserForwardListResponse(List<UserCopy> followings, String messageToBeForwarded) {
        this.followings = followings;
        this.messageToBeForwarded = messageToBeForwarded;
    }

    public List<UserCopy> getFollowings() {
        return followings;
    }

    public String getMessageToBeForwarded() {
        return messageToBeForwarded;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowUserForwardList(this);
    }
}