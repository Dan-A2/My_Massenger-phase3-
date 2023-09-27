package ir.sharif.ap.phase3.event.tweet;

import ir.sharif.ap.phase3.response.Response;

public interface TweetVisitor {
    Response visitAddComment(CommentEvent event);
    Response visitLikeORDislike(Dislike_LikeEvent event);
    Response visitForward(ForwardEvent event);
    Response visitReport(ReportEvent event);
    Response visitSave(SaveTweetEvent event);
    Response visitPost(PostTweetEvent event);
    Response visitShowComments(ShowCommentsEvent event);
}