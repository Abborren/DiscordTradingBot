package discordBot.bot.fileUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class FileManager {
    public BufferedImage load(File filename) {
        try {
            //System.out.println("Loading succeeded");
           return ImageIO.read(filename);
        } catch (IOException e) {
            System.out.println("Image Load Failed!");
            return null;
        }
    }
    public String loadString(File file) {
        try {
            return  new String(Files.readAllBytes(Paths.get(String.valueOf(file))));
        } catch (IOException e) {
            System.out.println(file.toString()+" not found or something went wrong!");
            return null;
        }
    }
    public void saveString(File file,String s) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(s);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] loadRefs(File filePath) {
        BufferedImage[] imageArray = new BufferedImage[5];
        for (int i = 0; i < imageArray.length;i++) {
            imageArray[i] = load(new File(filePath.toString()+i+".png"));
        }
        return imageArray;
    }
}
