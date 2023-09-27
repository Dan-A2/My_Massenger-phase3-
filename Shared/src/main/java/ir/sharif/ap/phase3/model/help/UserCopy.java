package ir.sharif.ap.phase3.model.help;

import ir.sharif.ap.phase3.model.main.User;

public class UserCopy {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final boolean isAccountPublic;
    private final String whoCanSeeLastSeen;
    private final String bio;
    private final String email;
    private final String birthday;
    private final String phoneNumber;
    private final boolean isActive;
    private final boolean isOnline;
    private final String lastSeen;
    private String profileImage;
    private final int tweetCount;
    private final int followerCount;
    private final int followingCount;

    public UserCopy(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isAccountPublic = user.isAccountPublic();
        this.whoCanSeeLastSeen = user.getWhoCanSeeLastSeen();
        this.bio = user.getBio();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.phoneNumber = user.getPhoneNumber();
        this.isActive = user.isActive();
        this.isOnline = user.isOnline();
        this.lastSeen = user.getLastSeen();
        this.tweetCount = user.getMyTweets().size();
        this.followerCount = user.getFollowers().size();
        this.followingCount = user.getFollowings().size();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAccountPublic() {
        return isAccountPublic;
    }

    public String getWhoCanSeeLastSeen() {
        return whoCanSeeLastSeen;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }
}