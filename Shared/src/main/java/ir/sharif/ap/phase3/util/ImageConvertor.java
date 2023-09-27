package ir.sharif.ap.phase3.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageConvertor {

    public Image convertData(byte[] imageData) {
        if (imageData == null)
            return null;
        ImageIcon imageIcon = new ImageIcon(imageData);
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        imageIcon.paintIcon(null, g, 0, 0);
        g.dispose();
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public byte[] selectImage(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertByte(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] convertString(String string) {
        return Base64.getDecoder().decode(string);
    }
}