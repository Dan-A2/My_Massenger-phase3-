package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public class ProfileEditEvent extends UserEvent {

    private final int userId;
    private final String firstname;
    private final String lastname;
    private final String username;
    private final String password;
    private final String email;
    private final String bio;
    private final String phoneNumber;
    private final String birthday;
    private byte[] image;

    public ProfileEditEvent(int id, String firstname, String lastname, String username, String password, String email, String bio, String phoneNumber, String birthday) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.userId = id;
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

    public int getUserId() {
        return userId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitEditProfile(this);
    }
}