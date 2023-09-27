package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.UserCopy;

public class SettingChangeResponse extends Response{

    private final UserCopy userCopy;

    public SettingChangeResponse(UserCopy userCopy) {
        this.userCopy = userCopy;
    }

    public UserCopy getUserCopy() {
        return userCopy;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitChangeSetting(this);
    }
}