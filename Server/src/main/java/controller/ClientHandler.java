package controller;

import database.Database;
import ir.sharif.ap.phase3.event.EvenToken;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.event.entrance.EntranceVisitor;
import ir.sharif.ap.phase3.event.entrance.LoginEvent;
import ir.sharif.ap.phase3.event.entrance.LogoutEvent;
import ir.sharif.ap.phase3.event.entrance.RegistrationEvent;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.response.*;
import ir.sharif.ap.phase3.util.Feedback;
import logic.ChatController;
import logic.EntranceController;
import util.UserCopyCreator;

public class ClientHandler extends Thread implements EntranceVisitor {

    private static ClientHandler handler;
    private final ResponseSender sender;
    private final int token;
    private final EventVisitor visitor;
    private int userId;

    public ClientHandler(ResponseSender sender, int token, EventVisitor visitor) {
        this.sender = sender;
        this.token = token;
        this.visitor = visitor;
        handler = this;
    }

    @Override
    public void run() {
        try {
            while (true) {
                EvenToken event = sender.getEvenToken();
                if (event.getToken() == null) {
                    sender.sendResponse(new TokenResponse(token));
                } else if (event.getToken() == token) {
                    sender.sendResponse(event.getEvent().visit(visitor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            User tmp = Database.get(userId, User.class);
            tmp.setOnline(false);
            tmp.setLastSeen();
            Database.update(tmp);
        }
    }

    @Override
    public Response visitLogin(LoginEvent loginEvent) {
        EntranceController controller = new EntranceController();
        ChatController controller1 = new ChatController();
        if (controller.isInputValidLogin(loginEvent.getUsername(), loginEvent.getPassword())) {
            User user = Database.getUser(loginEvent.getUsername());
            controller1.userIsOnline(user);
            user.setOnline(true);
            Database.update(user);
            UserCopy userCopy = UserCopyCreator.createUser(user);
            userId = user.getId();
            return new GoToMainMenuResponse(userCopy);
        } else {
            return new FeedbackResponse(Feedback.Login);
        }
    }

    @Override
    public Response visitRegister(RegistrationEvent registrationEvent) {
        if (registrationEvent.getUsername().startsWith("@")) {
            return new FeedbackResponse(Feedback.RegisterAt);
        }
        try {
            User user = new User(registrationEvent.getFirstname(), registrationEvent.getLastname(), registrationEvent.getUsername(), registrationEvent.getPassword(), registrationEvent.getEmail());
            if (registrationEvent.getBio() != null && !registrationEvent.getBio().equals("")) {
                user.setBio(registrationEvent.getBio());
            }
            if (registrationEvent.getBirthday() != null && !registrationEvent.getBirthday().equals("")) {
                user.setBirthday(registrationEvent.getBirthday());
            }
            if (registrationEvent.getPhoneNumber() != null && !registrationEvent.getPhoneNumber().equals("")) {
                user.setPhoneNumber(registrationEvent.getPhoneNumber());
            }
            user.setOnline(true);
            Database.save(user);
            do {
                userId = user.getId();
            } while (user.getId() == 0);
            return new GoToMainMenuResponse(new UserCopy(user));
        } catch (Exception e) {
            return new FeedbackResponse(Feedback.Register);
        }
    }

    @Override
    public Response visitLogout(LogoutEvent logoutEvent) {
        User user = Database.get(userId, User.class);
        user.setOnline(false);
        user.setLastSeen();
        Database.update(user);
        return new NoResponse();
    }

    public static ClientHandler getInstance() {
        return handler;
    }
}