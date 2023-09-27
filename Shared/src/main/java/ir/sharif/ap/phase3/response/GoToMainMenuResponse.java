package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.UserCopy;

public class GoToMainMenuResponse extends Response {

    private final UserCopy userCopy;

    public GoToMainMenuResponse(UserCopy userCopy) {
        this.userCopy = userCopy;
    }

    public UserCopy getUser() {
        return userCopy;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitGoToMain(this);
    }
}