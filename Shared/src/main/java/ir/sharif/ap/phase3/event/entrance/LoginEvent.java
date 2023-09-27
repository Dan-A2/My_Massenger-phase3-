package ir.sharif.ap.phase3.event.entrance;

import ir.sharif.ap.phase3.response.Response;

public class LoginEvent extends EntranceEvent {

    private final String username;
    private final String password;

    public LoginEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Response visit(EntranceVisitor visitor) {
        return visitor.visitLogin(this);
    }
}
