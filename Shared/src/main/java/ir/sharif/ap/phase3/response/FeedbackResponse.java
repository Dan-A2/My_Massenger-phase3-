package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.util.Feedback;

public class FeedbackResponse extends Response {

    private final Feedback feedback;

    public FeedbackResponse(Feedback feedback) {
        this.feedback = feedback;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitFeedback(this);
    }
}