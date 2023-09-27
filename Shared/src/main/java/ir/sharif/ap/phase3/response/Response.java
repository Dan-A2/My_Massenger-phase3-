package ir.sharif.ap.phase3.response;

public abstract class Response {
    public abstract void visit(ResponseVisitor visitor);
}