package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.util.COMMANDS;

public class DoRequestEvent extends UserEvent {

    private final int doerId;
    private final int requesterId;
    private final COMMANDS command;

    public DoRequestEvent(int doerId, int requesterId, COMMANDS command) {
        this.doerId = doerId;
        this.requesterId = requesterId;
        this.command = command;
    }

    public int getDoerId() {
        return doerId;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public COMMANDS getCommand() {
        return command;
    }

    @Override
    public Response visit(UserVisitor visitor) {
        return visitor.visitRequest(this);
    }
}