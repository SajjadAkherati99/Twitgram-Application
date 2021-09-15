package Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class ImageConverter {
    public static String toString(File file){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            String encodedFile = new String(Base64.getEncoder().encode(bytes));
            return encodedFile;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static Image toImage(String encodedImage) throws IOException {
            byte[] bytes1 = Base64.getDecoder().decode(encodedImage);
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes1);
            BufferedImage img = ImageIO.read(bis);
            return SwingFXUtils.toFXImage(img, null);
    }
}
