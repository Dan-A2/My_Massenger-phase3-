package database;

import ir.sharif.ap.phase3.model.main.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class Database {

    private static final Object object = new Object();

    public static <T> void save(T pojo) {
        synchronized (object) {
            Session session = Constents.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            System.out.println("saving: " + "\n" + pojo);
            session.save(pojo);
            transaction.commit();
            session.close();
        }
    }

    public static <T> void update(T pojo) {
        synchronized (object) {
            Session session = Constents.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            System.out.println("updating: " + "\n" + pojo);
            session.update(pojo);
            transaction.commit();
            session.close();
        }
    }

    public static <T> void delete(T pojo) {
        synchronized (object) {
            Session session = Constents.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            System.out.println("deleting " + pojo);
            session.delete(pojo);
            transaction.commit();
            session.close();
        }
    }

    public static <T extends Savable> T get(int id, Class<T> type) {
        synchronized (object) {
            Session session = Constents.getFactory().openSession();
            T t = session.get(type, id);
            session.close();
            return t;
        }
    }

    public static User getUser(String username) {
        synchronized (object) {
            Session session = Constents.getFactory().openSession();
            Query query = session.createQuery("from userPojo where username=:name").setParameter("name", username);
            System.out.println("getting user " + username);
            User user = (User) query.uniqueResult();
            session.close();
            return user;
        }
    }

    public static List<User> allUsers() {

        Session session = Constents.getFactory().openSession();
        Query query = session.createQuery("from userPojo where isActive=:activity").setParameter("activity", true);
        List<User> users = query.list();
        session.close();
        return users;

    }

    public static List<Tweet_Comment> getAllTweets() {
        synchronized (object) {
            Session session = Constents.getFactory().openSession();
            Query query = session.createQuery("from Tweet_Comment where isComment=:iscom").setParameter("iscom", false);
            System.out.println("getting the goddamn tweets");
            List<Tweet_Comment> tweet_comments = (List<Tweet_Comment>) query.list();
            session.close();
            return tweet_comments;
        }
    }

    public static Chat getChat(User u1, User u2) {
        synchronized (object) {
            Session session = Constents.getFactory().openSession();
            System.out.println("getting chat");
            Query query = session.createQuery("from Chat where (user1Id=:id1 AND user2Id=:id2) OR (user1Id=:id2 AND user2Id=:id1)").setParameter("id1", u1.getId()).setParameter("id2", u2.getId());
            Chat chat = (Chat) query.uniqueResult();
            session.close();
            return chat;
        }
    }
}