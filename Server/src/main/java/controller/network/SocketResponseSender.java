package controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ResponseSender;
import ir.sharif.ap.phase3.event.EvenToken;
import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.gson.Deserializer;
import ir.sharif.ap.phase3.gson.Serializer;
import ir.sharif.ap.phase3.response.Response;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketResponseSender implements ResponseSender {
    private final Socket socket;
    private PrintStream printStream;
    private Scanner scanner;
    private final Gson gson;

    public SocketResponseSender(Socket socket) {
        this.socket = socket;
        try {
            this.printStream = new PrintStream(socket.getOutputStream());
            this.scanner = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Exception in socket response sender");
            e.printStackTrace();
        }
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Event.class, new Deserializer<>())
                .registerTypeAdapter(Response.class, new Serializer<>())
                .create();
    }

    @Override
    public void sendResponse(Response response) {
        printStream.println(gson.toJson(response, Response.class));
    }

    @Override
    public EvenToken getEvenToken() {
        String event = scanner.nextLine();
        return gson.fromJson(event, EvenToken.class);
    }

    @Override
    public void close() {
        try {
            printStream.flush();
            printStream.close();
            scanner.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}