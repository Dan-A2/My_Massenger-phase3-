package ir.sharif.ap.phase3.event;

public class EvenToken {
    private final Event event;
    private final Integer token;

    public EvenToken(Event event, Integer token) {
        this.event = event;
        this.token = token;
    }

    public Event getEvent() {
        return event;
    }

    public Integer getToken() {
        return token;
    }
}