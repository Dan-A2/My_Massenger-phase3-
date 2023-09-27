package org.openjfx.connector.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.ap.phase3.event.EvenToken;
import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.gson.Deserializer;
import ir.sharif.ap.phase3.gson.Serializer;
import ir.sharif.ap.phase3.response.Response;
import org.openjfx.connector.EventSender;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketEventSender implements EventSender {
    private final Socket socket;
    private final Scanner scanner;
    private final PrintStream printStream;
    private final Gson gson;

    public SocketEventSender(Socket socket) throws IOException {
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.printStream = new PrintStream(socket.getOutputStream());
        this.gson = new GsonBuilder().
                registerTypeAdapter(Response.class, new Deserializer<>()).
                registerTypeAdapter(Event.class, new Serializer<>()).
                create();
    }

    @Override
    public Response send(EvenToken evenToken) {
        String eventString = gson.toJson(evenToken, EvenToken.class);
        printStream.println(eventString);
        String responseString = scanner.nextLine();
        return gson.fromJson(responseString, Response.class);
    }

    @Override
    public void close() {
        try {
            scanner.close();
            printStream.flush();
            printStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}