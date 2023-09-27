package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class ChangeSettingsEvent extends UserEvent {

    private final int userId;
    private final boolean accountPublicity;
    private final String whoCanSeeLastSeen;
    private final boolean accountActivity;

    public ChangeSettingsEvent(int userId, boolean accountPublicity, String whoCanSeeLastSeen, boolean accountActivity) {
        this.userId = userId;
        this.accountPublicity = accountPublicity;
        this.whoCanSeeLastSeen = whoCanSeeLastSeen;
        this.accountActivity = accountActivity;
    }

    public boolean isAccountPublicity() {
        return accountPublicity;
    }

    public String getWhoCanSeeLastSeen() {
        return whoCanSeeLastSeen;
    }

    public boolean isAccountActivity() {
        return accountActivity;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitChangeSetting(this);
    }
}