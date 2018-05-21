package discordBot.main.FileUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManager {
    public BufferedImage load(File filename) {
        try {
            System.out.println("Loading succeeded");
           return ImageIO.read(filename);
        } catch (IOException e) {
            System.out.println("Image Load Failed!");
        }
        return null;
    }

    public BufferedImage[] loadRefs(File file) {
        BufferedImage[] imageArray = new BufferedImage[5];
        for (int i = 0; i < imageArray.length;i++) {
            imageArray[i] = load(new File("Images/Downloaded/Refs/ref"+i+".png"));
        }
        return imageArray;
    }
}
