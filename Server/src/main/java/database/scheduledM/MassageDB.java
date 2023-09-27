package database.scheduledM;

import ir.sharif.ap.phase3.model.help.ScheduledMessage;
import ir.sharif.ap.phase3.util.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MassageDB {

    private static final Config config = Config.getConfig("saveNLoad");

    public static void saveImageID() {
        String path = config.getProperty(String.class, "massageLastId");
        try {
            File file = new File(path);
            for (File file1 : file.listFiles()){
                file1.delete();
            }
            File massageId = new File(path + config.getProperty(String.class, "lastId"));
            massageId.createNewFile();
            PrintStream printStream1 = new PrintStream(new FileOutputStream(massageId, true));
            printStream1.println(ScheduledMessage.getLastId());
            printStream1.flush();
            printStream1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageID() {
        String path = config.getProperty(String.class, "massageLastId") + config.getProperty(String.class, "lastId");
        try {
            File file = new File(path);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                int lastId = Integer.parseInt(scanner.next());
                ScheduledMessage.setLastId(lastId);
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}