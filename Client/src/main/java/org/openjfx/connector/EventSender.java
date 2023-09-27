package org.openjfx.connector;

import ir.sharif.ap.phase3.event.EvenToken;
import ir.sharif.ap.phase3.response.Response;

public interface EventSender {
    Response send(EvenToken evenToken);

    void close();
}