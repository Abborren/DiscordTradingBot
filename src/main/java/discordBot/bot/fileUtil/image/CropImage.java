package discordBot.bot.fileUtil.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Put in picture, coordinates and size and it will spit back a image
 */
class CropImage {
    private Compare compare = new Compare();
    void createSubImages(BufferedImage bigImage) {

        BufferedImage border = ImageLogic.fileManager.load(new File("Images/Border/Border.png"));
        int[] coordinates = compare.findSubImage(bigImage, border,0.01);
        int counter = 0;
        int refNumber = 0;
        outerLoop:
        for(int rows = 0; rows < 3; rows++) {
            for(int columns = 0; columns < 12; columns++) {
                counter++;
                BufferedImage subImage = bigImage.getSubimage((columns<6?coordinates[0]+(columns*58):coordinates[0]+32+(columns*58))-1, coordinates[1]+(rows*100) ,48, 48);
                if (counter == 5) {
                    try {
                        System.out.println("img ref"+refNumber+" saved!");
                        ImageIO.write(subImage, "png", new File(String.format("Images/Downloaded/Input/ref%s.png", refNumber)));
                        refNumber++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    counter = -1;
                    if (refNumber == 4) {
                        break outerLoop;
                    }
                }
                if (rows == 2 && columns == 7) {
                    break;
                }
            }
        }
    }
}
