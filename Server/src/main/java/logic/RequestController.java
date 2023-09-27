package logic;

import database.Database;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestController {

    static private final Logger logger = (Logger) LogManager.getLogger(NotificationHandler.class);

    public void acceptRequest(User doer, User requester) {
        Config config = Config.getConfig("mainmenu");
        boolean contains = false;
        for (User u : doer.getFollowers()) {
            if (u.getId() == requester.getId()) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            doer.getFollowers().add(requester);
            requester.getFollowings().add(doer);
            for (User r : doer.getRequesters()) {
                if (r.getId() == requester.getId()) {
                    doer.getRequesters().remove(r);
                    break;
                }
            }
        }
        NotificationHandler handler = new NotificationHandler();
        handler.sendNotif(config.getProperty(String.class,"user") + " " + doer.getUsername() + " " + config.getProperty(String.class,"followRequestAccept"), requester, doer);
        logger.info("User " + doer.getUsername() + " has accepted the follow request of " + requester.getUsername());
        ignoreRequest(doer, requester);
    }

    public void ignoreRequest(User doer, User requester) {
        for (User user : doer.getRequesters()) {
            if (user.getId() == requester.getId()) {
                doer.getRequesters().remove(user);
                break;
            }
        }
        logger.info("User " + doer.getUsername() + " has ignored the follow request of " + requester.getUsername());
        Database.update(doer);
        Database.update(requester);
    }

    public void denyRequest(User doer, User requester) {
        Config config = Config.getConfig("mainmenu");
        logger.info("User " + doer.getUsername() + " has denied the follow request of " + requester.getUsername());
        ignoreRequest(doer, requester);
        NotificationHandler handler = new NotificationHandler();
        handler.sendNotif(config.getProperty(String.class,"user") + " " + doer.getUsername() + " " + config.getProperty(String.class,"followRequestDeny") ,requester, doer);
    }

}