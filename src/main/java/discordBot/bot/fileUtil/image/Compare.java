package discordBot.bot.fileUtil.image;


import java.awt.image.BufferedImage;

class Compare {
    /**
     * finds a region of im1 that matches with im2, if no matches are found then returns 0,0
     * @param im1 image one that will be compared
     * @param im2 Sub image, compares to image 1
     * @return returns a coordinate x and y on match or 0,0 if it can not find a match
     */
    int[] findSubImage(BufferedImage im1, BufferedImage im2, double matchLimit) {
        int w1 = im1.getWidth();
        int h1 = im1.getHeight();
        int w2 = im2.getWidth();
        int h2 = im2.getHeight();
        assert (w2 <= w1 && h2 <= h1);
        double[] temp = subImageLoop(w1,w2,h1,h2,im1,im2,matchLimit);
        // output similarity measure from 0 to 1, with 0 being identical
        System.out.println(temp[2]);
        // return best location
        //System.out.println("border found at "+ temp[0]+","+ temp[1]);
        return new int[] {(int) temp[0], (int) temp[1]};
    }

    /**
     * same as findSubImage except that it returns a double instead of int coordinates
     * @param im1 Image One that will be compared
     * @param im2 Sub Image, the thing it will look for
     * @return a % value of matching
     */
    double findSubImageDouble(BufferedImage im1, BufferedImage im2, double matchLimit) {
        int w1 = im1.getWidth();
        int h1 = im1.getHeight();
        int w2 = im2.getWidth();
        int h2 = im2.getHeight();
        assert (w2 <= w1 && h2 <= h1);
        // will keep track of best position found

        double[] temp = subImageLoop(w1,w2,h1,h2,im1,im2,matchLimit);
        if (w1-w2 == 0 && h1-h2 == 0 && temp[2] == Double.POSITIVE_INFINITY) {
            return 0.0;
        }
        // output similarity measure from 0 to 1, with 0 being identical

        // return best location

        return temp[2];
    }
    private double[] subImageLoop(int w1,int w2,int h1,int h2,BufferedImage im1,BufferedImage im2,double matchLimit) {
        // will keep track of best position found
        int bestX = 0;
        int bestY = 0;
        double lowestDiff = Double.POSITIVE_INFINITY;

        // brute-force search through whole image (slow...)
        outerLoop:
        for (int x = 0; x < w1 - w2; x++) {
            for (int y = 0; y < h1 - h2; y++) {
                double comp = compareImages(im1.getSubimage(x, y, w2, h2), im2);
                if (comp < lowestDiff) {
                    bestX = x;
                    bestY = y;
                    lowestDiff = comp;
                    if(lowestDiff < matchLimit)
                    {
                        //System.out.println(String.format("Best match found at %s - %s with a similarity of %s", bestX, bestY, lowestDiff));
                        break outerLoop;
                    }
                }
            }
        }
        return new double[] {bestX,bestY,lowestDiff};
    }
    /**
     * Determines how different two identically sized regions are.
     */
    private double compareImages(BufferedImage im1, BufferedImage im2){
        assert(im1.getHeight() == im2.getHeight() && im1.getWidth() == im2.getWidth());
        double variation = 0.0;
        for(int x = 0;x < im1.getWidth();x++){
            for(int y = 0;y < im1.getHeight();y++){
                variation += compareARGB(im1.getRGB(x,y),im2.getRGB(x,y))/Math.sqrt(3);
            }
        }
        return variation/(im1.getWidth()*im1.getHeight());
    }

    /**
     * Calculates the difference between two ARGB colours (BufferedImage.TYPE_INT_ARGB).
     */
    private double compareARGB(int rgb1, int rgb2){
        double r1 = ((rgb1 >> 16) & 0xFF)/255.0; double r2 = ((rgb2 >> 16) & 0xFF)/255.0;
        double g1 = ((rgb1 >> 8) & 0xFF)/255.0;  double g2 = ((rgb2 >> 8) & 0xFF)/255.0;
        double b1 = (rgb1 & 0xFF)/255.0;         double b2 = (rgb2 & 0xFF)/255.0;
        double a1 = ((rgb1 >> 24) & 0xFF)/255.0; double a2 = ((rgb2 >> 24) & 0xFF)/255.0;
        // if there is transparency, the alpha values will make difference smaller
        return a1*a2*Math.sqrt((r1-r2)*(r1-r2) + (g1-g2)*(g1-g2) + (b1-b2)*(b1-b2));
    }

}
