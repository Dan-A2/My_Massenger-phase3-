package database.scheduledM;

import com.google.gson.Gson;
import ir.sharif.ap.phase3.model.help.ScheduledMessage;
import ir.sharif.ap.phase3.util.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MassageSaver {

    private static final File mainFile = new File(Config.getConfig("saveNLoad").getProperty(String.class, "massageAddress"));
    private static final Gson gson = new Gson();

    public static ScheduledMessage getMassage(int id) {
        try {
            for (File file : mainFile.listFiles()) {
                if (file.getName().equals(id + ".txt")) {
                    Scanner scanner = new Scanner(file);
                    String json = scanner.nextLine();
                    return gson.fromJson(json, ScheduledMessage.class);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveMassage(ScheduledMessage message) {
        try {
            File file = new File(Config.getConfig("saveNLoad").getProperty(String.class, "massageAddress") + message.getId()
                    + Config.getConfig("saveNLoad").getProperty(String.class, "txtFile"));
            file.createNewFile();
            PrintStream printStream = new PrintStream(new FileOutputStream(file, true));
            printStream.println(gson.toJson(message));
            printStream.flush();
            printStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}