package database;

import ir.sharif.ap.phase3.model.main.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Constents {

    private final static Configuration configuration = new Configuration().configure().
            addAnnotatedClass(User.class).addAnnotatedClass(Tweet_Comment.class).addAnnotatedClass(Chat.class)
            .addAnnotatedClass(Notification.class).addAnnotatedClass(GroupChat.class).addAnnotatedClass(Message.class)
            .addAnnotatedClass(UserList.class);
    private final static SessionFactory factory = configuration.buildSessionFactory();

    public static SessionFactory getFactory() {
        return factory;
    }
}