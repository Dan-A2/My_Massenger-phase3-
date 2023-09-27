package database.bot;

import ir.sharif.ap.phase3.util.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class BotDatabase {

    private static final Config config = Config.getConfig("saveNLoad");

    public static void saveBotFields(int botId, String fields) {
        synchronized (config) {
            String path = config.getProperty(String.class, "botMotherAddress");
            try {
                File file = new File(path);
                for (File f : file.listFiles()) {
                    if (f.getName().equals(botId + ".txt")) {
                        f.delete();
                        break;
                    }
                }
                File save = new File(path + "\\" + botId + ".txt");
                save.createNewFile();
                PrintStream printStream = new PrintStream(new FileOutputStream(save, true));
                printStream.println(fields);
                printStream.flush();
                printStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String loadBotFields(int botId) {
        synchronized (config) {
            String fields = "";
            String path = config.getProperty(String.class, "botMotherAddress") + "\\" + botId + ".txt";
            File file = new File(path);
            try {
                if (file.exists()) {
                    Scanner scanner = new Scanner(file);
                    fields = scanner.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fields;
        }
    }
}