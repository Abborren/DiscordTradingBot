package discordBot.bot.fileUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {
    /**
     * this will load a specific image
     * @param filename the pathname of the image to be loaded
     * @return returns the buffered image of the path that was loaded
     */
    public BufferedImage load(File filename) {
        try {
            //System.out.println("Loading succeeded");
           return ImageIO.read(filename);
        } catch (IOException e) {
            System.out.println("Image Load Failed!");
            return null;
        }
    }

    /**
     * this will load all strings of a path
     * @param file the specific file path
     * @return returns the loaded String
     */
    public String loadString(File file) {
        try {
            return  new String(Files.readAllBytes(Paths.get(String.valueOf(file))));
        } catch (IOException e) {
            System.exit(0);
            System.out.println("File load failed of"+file.toString());
            return null;
        }
    }

    /**
     * this will load all lines of a file into a String array
     * @param file the file path of the txt file
     * @param needOnlyFirstItem if it only needs every other item instead of all of them
     * @return returns the loaded String array
     */
    public String[] loadStringArray(File file,boolean needOnlyFirstItem) {
        try {
            BufferedReader br = new BufferedReader( new FileReader(file));
            String strLine;
            ArrayList<String> strings = new ArrayList<>();
            while ((strLine = br.readLine()) != null) {
                strings.add(strLine);
            }
            br.close();
            if (needOnlyFirstItem) {
                ArrayList<String> outputStrings = new ArrayList<>();
                for (int i = 0; i< strings.size();) {
                    outputStrings.add(strings.get(i));
                    i = i+2;
                }
                return outputStrings.toArray(new String[0]);
            } else {
                return strings.toArray(new String[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
        System.out.println("File load failed of"+file.toString());
        return null;
    }

    /**
     * this will load all the lines of a specific txt file
     * @param file the file path of a specific file
     * @return will return a 2d String array with the first[0][0] being item 0 and item 1 being [0][1] and item 2 being [1][0] etc
     */
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

    /**
     * this tries to save String to a file
     * @param file the specific filepath where s will be saved
     * @param s the specific String to be saved
     */
    public void saveString(File file,String s) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(s);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this will load the references
     * @param filePath the specific filePath
     * @return return the BufferedImage array with the references
     */
    public BufferedImage[] loadRefs(File filePath) {
        BufferedImage[] imageArray = new BufferedImage[5];
        for (int i = 0; i < imageArray.length;i++) {
            imageArray[i] = load(new File(filePath.toString()+i+".png"));
        }
        return imageArray;
    }
}
