package ir.sharif.ap.phase3.model.main;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity(name = "userPojo")
public class User implements Savable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean isAccountPublic;
    private String whoCanSeeLastSeen;
    private String bio;
    private String Email;
    private String birthday;
    private String phoneNumber = "";
    private boolean isActive;
    private boolean isOnline;
    private String lastSeen;
    private Integer profileImageId;
    @ManyToMany
    @JoinTable(name = "user_friends")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> friends = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "user_followers")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> followers = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "user_followings")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> followings = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "user_blacklist")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> blackList = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "user_muted")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> muted = new LinkedList<>();
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<Chat> myChats = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "user_savedTweets")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tweet_Comment> savedTweets = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "user_savedMassages")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> savedMassages = new LinkedList<>();
    @OneToMany
    @JoinTable(name = "user_savedTexts")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> savedTextsOfMe = new LinkedList<>();
    @OneToMany(mappedBy = "sender")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tweet_Comment> myTweets = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "user_requesters")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> requesters = new LinkedList<>();
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "user_likedTweets")
    private List<Tweet_Comment> likedTweets = new LinkedList<>();
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Notification> myNotifs = new LinkedList<>();
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GroupChat> groups = new LinkedList<>();
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GroupChat> leftGroups = new LinkedList<>();
    @OneToMany
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, UserList> mySortings = new HashMap<>();
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<Integer, Integer> unseenMassagesGroups = new HashMap<>();

    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.Email = email;
        this.setOnline(true);
        this.setAccountPublic(true);
        this.setActive(true);
        this.setWhoCanSeeLastSeen("everybody");
    }

    public User() {}

    public User(String username) {
        this.username = username;
        firstName = "";
        lastName = "";
        password = "";
        isAccountPublic = true;
        whoCanSeeLastSeen = "everybody";
        bio = "";
        Email = "";
        birthday = "";
        phoneNumber = "";
        isActive = true;
        isOnline = true;
        lastSeen = "";
        profileImageId = -1;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountPublic() {
        return isAccountPublic;
    }

    public void setAccountPublic(boolean accountPublic) {
        isAccountPublic = accountPublic;
    }

    public String getWhoCanSeeLastSeen() {
        return whoCanSeeLastSeen;
    }

    public void setWhoCanSeeLastSeen(String whoCanSeeLastSeen) {
        this.whoCanSeeLastSeen = whoCanSeeLastSeen;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.birthday = birthday1.format(formatter);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen() {
        LocalDateTime lastSeenMolayye = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.lastSeen = lastSeenMolayye.format(formatter);
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public List<User> getBlackList() {
        return blackList;
    }

    public List<User> getMuted() {
        return muted;
    }

    public List<Chat> getMyChats() {
        return myChats;
    }

    public List<Tweet_Comment> getSavedTweets() {
        return savedTweets;
    }

    public List<Message> getSavedMassages() {
        return savedMassages;
    }

    public List<Message> getSavedTextsOfMe() {
        return savedTextsOfMe;
    }

    public List<Tweet_Comment> getMyTweets() {
        return myTweets;
    }

    public List<User> getRequesters() {
        return requesters;
    }

    public List<GroupChat> getLeftGroups() {
        return leftGroups;
    }

    public void setLeftGroups(List<GroupChat> leftGroups) {
        this.leftGroups = leftGroups;
    }

    public List<Tweet_Comment> getLikedTweets() {
        return likedTweets;
    }

    public List<Notification> getMyNotifs() {
        return myNotifs;
    }

    public List<GroupChat> getGroups() {
        return groups;
    }

    public Map<String, UserList> getMySortings() {
        return mySortings;
    }

    public Map<Integer, Integer> getUnseenMassagesGroups() {
        return unseenMassagesGroups;
    }

    public Integer getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(Integer profileImageId) {
        this.profileImageId = profileImageId;
    }

    @PostLoad
    public void postLoad() {
        this.friends = new LinkedList<>(this.friends);
        this.followers = new LinkedList<>(this.followers);
        this.followings = new LinkedList<>(this.followings);
        this.blackList = new LinkedList<>(this.blackList);
        this.muted = new LinkedList<>(this.muted);
        this.savedTweets = new LinkedList<>(this.savedTweets);
        this.savedMassages = new LinkedList<>(this.savedMassages);
        this.savedTextsOfMe = new LinkedList<>(this.savedTextsOfMe);
        this.myTweets = new LinkedList<>(this.myTweets);
        this.requesters = new LinkedList<>(this.requesters);
        this.likedTweets = new LinkedList<>(this.likedTweets);
        this.myNotifs = new LinkedList<>(this.myNotifs);
        this.groups = new LinkedList<>(this.groups);
        this.leftGroups = new LinkedList<>(this.leftGroups);
        this.mySortings = new HashMap<>(this.mySortings);
        this.unseenMassagesGroups = new HashMap<>(this.unseenMassagesGroups);
    }

    @Override
    public String toString() {
        return "username: " + username + "\n"
                + "email: " + Email + "\n"
                + "birthday: " + birthday + "\n"
                + "profileImage: " + profileImageId;
    }
}