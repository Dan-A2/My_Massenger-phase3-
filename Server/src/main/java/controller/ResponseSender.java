package controller;

import ir.sharif.ap.phase3.event.EvenToken;
import ir.sharif.ap.phase3.response.Response;

public interface ResponseSender {
    void sendResponse(Response response);

    EvenToken getEvenToken();

    void close();
}