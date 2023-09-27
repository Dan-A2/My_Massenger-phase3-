package ir.sharif.ap.phase3.model.main;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GroupChat implements Savable{

    @ManyToMany(mappedBy = "groups")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;
    private String groupName;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> messages;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public GroupChat(String groupName) {
        this.groupName = groupName;
        users = new LinkedList<>();
        messages = new LinkedList<>();
//        Database.save(this);
    }

    public GroupChat() {}

    public List<User> getUsers() {
        return users;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public int getId() {
        return id;
    }

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setId(int id) {
        this.id = id;
    }
}