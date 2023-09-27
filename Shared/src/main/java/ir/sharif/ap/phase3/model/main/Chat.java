package ir.sharif.ap.phase3.model.main;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Chat implements Savable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @ManyToOne
    private User user1;
    @ManyToOne
    private User user2;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> messages;
    private int user1UnseenMassages;
    private int user2UnseenMassages;
    private int user1Id, user2Id;

    public Chat(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        user1.getMyChats().add(this);
        user2.getMyChats().add(this);
        this.user1Id = user1.getId();
        this.user2Id = user2.getId();
        this.messages = new LinkedList<>();
        user1UnseenMassages = 0;
        user2UnseenMassages = 0;
//        Database.save(this);
//        Database.update(user1);
//        Database.update(user2);
    }

    public Chat() {
    }

    public User getUser1() {
        if(user1.isActive()){
            return user1;
        }
        return null;
    }

    public User getUser2() {
        if(user2.isActive()){
            return user2;
        }
        return null;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public int getUser1UnseenMassages() {
        return user1UnseenMassages;
    }

    public void setUser1UnseenMassages(int user1UnseenMassages) {
        this.user1UnseenMassages = user1UnseenMassages;
    }

    public int getUser2UnseenMassages() {
        return user2UnseenMassages;
    }

    public void setUser2UnseenMassages(int user2UnseenMassages) {
        this.user2UnseenMassages = user2UnseenMassages;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }
}