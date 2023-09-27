package ir.sharif.ap.phase3.event.general;

import ir.sharif.ap.phase3.response.Response;

public class TokenRequest extends GeneralEvent{
    @Override
    public Response visit(GeneralVisitor visitor) {
        return visitor.visitToken();
    }
}
