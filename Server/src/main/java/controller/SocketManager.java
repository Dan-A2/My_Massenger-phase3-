package controller;

import controller.network.SocketResponseSender;
import controller.visitors.EventVisitorImplementation;
import logic.ScheduleMassageController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

public class SocketManager extends Thread {
    @Override
    public void run() {
        System.out.println("socket manager started");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SecureRandom secureRandom = new SecureRandom();
        ScheduleMassageController controller = new ScheduleMassageController();
        controller.start();
        while (true) {
            ClientHandler clientHandler;
            int token = secureRandom.nextInt();
            try {
                Socket socket = serverSocket.accept();
                System.out.println("a new user came");
                clientHandler = new ClientHandler(new SocketResponseSender(socket), token, new EventVisitorImplementation());
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}