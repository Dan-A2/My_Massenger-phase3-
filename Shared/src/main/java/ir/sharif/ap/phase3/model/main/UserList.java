package ir.sharif.ap.phase3.model.main;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserList implements Savable{

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public UserList(List<User> users) {
        this.users = users;
    }

    public UserList() {
    }

    public List<User> getUsers() {
        return users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}