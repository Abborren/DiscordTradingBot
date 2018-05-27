package discordBot.bot.fileUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
            System.exit(0);
            System.out.println("File load failed of"+file.toString());
            return null;
        }
    }
    public String[] loadStringArray(File file) {
        try {
            BufferedReader br = new BufferedReader( new FileReader(file));
            String strLine;
            ArrayList<String> strings = new ArrayList<>();
            while ((strLine = br.readLine()) != null) {
                strings.add(strLine);
            }
            br.close();
            return strings.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
        System.out.println("File load failed of"+file.toString());
        return null;
    }
    public String[][] loadString2dArray(File file) {
        try {
            BufferedReader br = new BufferedReader( new FileReader(file));
            String strLine;
            ArrayList<String> strings = new ArrayList<>();
            while ((strLine = br.readLine()) != null) {
                strings.add(strLine);
            }
            br.close();

            String[][] array = new String[strings.size()/2][2];
            for (int i = 0; i < array.length; i++) {
                array[i][0] = strings.get(((i+1)*2)-2);
                array[i][1] = strings.get(((i+1)*2)-1);
            }
            return array;
        } catch (IOException h) {
            h.printStackTrace();
        }
        System.exit(0);
        System.out.println("File load failed of"+file.toString());
        return null;
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
