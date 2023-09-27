package ir.sharif.ap.phase3.response;

public class TokenResponse extends Response{

    private final int token;

    public TokenResponse(int token) {
        this.token = token;
    }

    public int getToken() {
        return token;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitToken(this);
    }
}