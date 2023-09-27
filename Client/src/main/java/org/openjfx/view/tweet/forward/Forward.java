package org.openjfx.view.tweet.forward;

import ir.sharif.ap.phase3.event.message.ShowForwardListEvent;
import ir.sharif.ap.phase3.util.Config;
import org.openjfx.connector.EventListener;

import javax.swing.*;

public class Forward {

    private final int forwardFromId;
    private final String tweetText;
    private byte[] image;

    public Forward(int forwardFromId, String tweetText) {
        this.forwardFromId = forwardFromId;
        this.tweetText = tweetText;
    }

    public void show(EventListener listener){
        Config config = Config.getConfig("tweet");
        String[] options = {config.getProperty(String.class,"forwardOption1"), config.getProperty(String.class,"forwardOption2")};
        int ans = JOptionPane.showOptionDialog(null, config.getProperty(String.class,"forwardMassage"),
                config.getProperty(String.class,"forwardTitle"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (ans == 0 || ans == 1) {
            ShowForwardListEvent event = new ShowForwardListEvent(forwardFromId, ans, tweetText);
            listener.listen(event);
        }
    }

    public int getForwardFromId() {
        return forwardFromId;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}