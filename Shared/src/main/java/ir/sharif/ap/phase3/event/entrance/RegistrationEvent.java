package ir.sharif.ap.phase3.event.entrance;

import ir.sharif.ap.phase3.event.user.UserEvent;
import ir.sharif.ap.phase3.event.user.UserVisitor;
import ir.sharif.ap.phase3.response.Response;

public class RegistrationEvent extends EntranceEvent {

    private final String firstname;
    private final String lastname;
    private final String username;
    private final String password;
    private final String email;
    private final String bio;
    private final String phoneNumber;
    private final String birthday;

    public RegistrationEvent(String firstname, String lastname, String username, String password, String email, String bio, String phoneNumber, String birthday) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    @Override
    public Response visit(EntranceVisitor visitor) {
        return visitor.visitRegister(this);
    }
}
