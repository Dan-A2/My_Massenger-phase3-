package ir.sharif.ap.phase3.bot;

public class BotResponse {
    private String sendTo;
    private String receiver;
    private String Message;

    /*
   the structure of String Array answer :
   first element:
       introduces the type of the answer;
       1 : message in Pv
       2 : message in Group
       3 : tweet
       4 : comment
   second element:
       introduces the receivers :
       1 : receiver(s) Id
       2 : group Id
       3 : 0
       4 : tweet Id
   third element:
       introduces the text
    */
    public BotResponse() {
    }

    public String getSendTo() {
        return this.sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }
}
