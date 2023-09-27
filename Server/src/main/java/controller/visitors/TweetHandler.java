package controller.visitors;

import database.Database;
import ir.sharif.ap.phase3.event.tweet.*;
import ir.sharif.ap.phase3.model.help.CommentPageFiller;
import ir.sharif.ap.phase3.model.help.TweetFiller;
import ir.sharif.ap.phase3.model.main.Tweet_Comment;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.response.FeedbackResponse;
import ir.sharif.ap.phase3.response.NoResponse;
import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.response.ShowCommentsResponse;
import ir.sharif.ap.phase3.util.Feedback;
import logic.SendingMassageController;
import logic.Tweet_CommentController;
import logic.UserController;
import util.TweetFillerCreator;

import java.util.LinkedList;
import java.util.List;

public class TweetHandler implements TweetVisitor {

    private static TweetHandler tweetHandler;

    private TweetHandler() {}

    public static TweetHandler getInstance() {
        if (tweetHandler == null)
            tweetHandler = new TweetHandler();
        return tweetHandler;
    }

    @Override
    public Response visitAddComment(CommentEvent commentEvent) {
        User user = Database.get(commentEvent.getSenderId(), User.class);
        Tweet_Comment tweet_comment = Database.get(commentEvent.getTweetId(), Tweet_Comment.class);
        Tweet_CommentController controller = new Tweet_CommentController();
        controller.addComment(commentEvent.getTxt(), user, tweet_comment);
        return new NoResponse();
    }

    @Override
    public Response visitLikeORDislike(Dislike_LikeEvent dislike_likeEvent) {
        Tweet_CommentController controller1 = new Tweet_CommentController();
        UserController controller2 = new UserController();
        User user = Database.get(dislike_likeEvent.getUserId(), User.class);
        Tweet_Comment t = Database.get(dislike_likeEvent.getTweetId(), Tweet_Comment.class);
        if (dislike_likeEvent.isLike()) {
            controller1.like(t);
            controller2.like(user, t);
        } else {
            controller1.dislike(t);
            controller2.dislike(user, t);
        }
        return new NoResponse();
    }

    @Override
    public Response visitForward(ForwardEvent forwardEvent) {
        SendingMassageController controller = new SendingMassageController();
        User forwardFrom = Database.get(forwardEvent.getForwardFromId(), User.class);
        User forwardTo = Database.get(forwardEvent.getForwardToId(), User.class);
        controller.sendMassage1(forwardFrom, forwardTo, forwardEvent.getTweetText(), true, null);
        return new NoResponse();
    }

    @Override
    public Response visitReport(ReportEvent reportEvent) {
        Tweet_CommentController controller = new Tweet_CommentController();
        Tweet_Comment t = Database.get(reportEvent.getTweetId(), Tweet_Comment.class);
        controller.report(t);
        return new NoResponse();
    }

    @Override
    public Response visitSave(SaveTweetEvent saveTweetEvent) {
        User user = Database.get(saveTweetEvent.getSaverId(), User.class);
        Tweet_Comment t = Database.get(saveTweetEvent.getTweetId(), Tweet_Comment.class);
        UserController controller = new UserController();
        if (saveTweetEvent.isSave()) {
            controller.saveTweet(user, t);
        } else {
            controller.unSaveTweet(user, t);
        }
        return new NoResponse();
    }

    @Override
    public Response visitPost(PostTweetEvent postTweetEvent) {
        Tweet_CommentController controller = new Tweet_CommentController();
        User user = Database.get(postTweetEvent.getSenderID(), User.class);
        controller.postTweet(postTweetEvent.getText(), user, postTweetEvent.getImage());
        return new FeedbackResponse(Feedback.TweetPosted);
    }

    @Override
    public Response visitShowComments(ShowCommentsEvent showCommentsEvent) {
        Tweet_Comment mother = Database.get(showCommentsEvent.getMainTweetId(), Tweet_Comment.class);
        User showTo = Database.get(showCommentsEvent.getShowToId(), User.class);
        List<TweetFiller> comments = new LinkedList<>();
        for (Tweet_Comment comment : mother.getComments()) {
            comments.add(TweetFillerCreator.createTweet(comment, showTo));
        }
        CommentPageFiller filler = new CommentPageFiller(TweetFillerCreator.createTweet(mother, showTo), comments);
        return new ShowCommentsResponse(filler, false);
    }
}