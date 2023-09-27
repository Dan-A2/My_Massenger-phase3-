package ir.sharif.ap.phase3.event.entrance;

import ir.sharif.ap.phase3.response.Response;

public interface EntranceVisitor {
    Response visitLogin(LoginEvent event);
    Response visitRegister(RegistrationEvent event);
    Response visitLogout(LogoutEvent event);
}