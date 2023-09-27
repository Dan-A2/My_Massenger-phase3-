package database.image;

import ir.sharif.ap.phase3.model.main.Image;
import ir.sharif.ap.phase3.util.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ImageDB {

    private static final Config config = Config.getConfig("saveNLoad");

    public static void saveImageID() {
        String path = config.getProperty(String.class, "imageLastId");
        try {
            File file = new File(path);
            for (File file1 : file.listFiles()){
                file1.delete();
            }
            File imageId = new File(path + config.getProperty(String.class, "lastId"));
            imageId.createNewFile();
            PrintStream printStream1 = new PrintStream(new FileOutputStream(imageId, true));
            printStream1.println(Image.getLastID());
            printStream1.flush();
            printStream1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageID() {
        String path = config.getProperty(String.class, "imageLastId") + config.getProperty(String.class, "lastId");
        try {
            File file = new File(path);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                int lastId = Integer.parseInt(scanner.next());
                Image.setLastID(lastId);
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}