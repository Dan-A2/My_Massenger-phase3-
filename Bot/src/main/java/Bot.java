import ir.sharif.ap.phase3.bot.BotResponse;

public interface Bot {
    BotResponse respond(String command, int senderId);
    String getFields();
    void setFields(String info);
}