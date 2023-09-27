import controller.SocketManager;
import controller.bot.BotHandler;
import database.image.ImageDB;
import database.scheduledM.MassageDB;

public class Main {
    public static void main(String[] args) {
        ImageDB.loadImageID();
        MassageDB.loadImageID();
        BotHandler botHandler = new BotHandler();
        SocketManager socketManager = new SocketManager();
        botHandler.start();
        socketManager.start();
    }
}