package database.image;

import ir.sharif.ap.phase3.util.Config;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;

public class ImageSaver {

    private final File mainFile = new File(Config.getConfig("saveNLoad").getProperty(String.class, "imageAddress"));

    public File getImage(int id) {
        try {
            for (File file : mainFile.listFiles()) {
                if (file.getName().equals(id + ".png")) {
                    return file;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int saveImage(Image image) {
        ir.sharif.ap.phase3.model.main.Image image1 = new ir.sharif.ap.phase3.model.main.Image();
        try {
            File data = new File(mainFile, image1.getId() + ".png");
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", data);
            ImageDB.saveImageID();
            return image1.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}