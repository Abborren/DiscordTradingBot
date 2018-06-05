package discordBot.bot.fileUtil.image;

import discordBot.bot.Bot;
import discordBot.bot.botIO.output.ChannelHandling.ChannelManager;
import discordBot.bot.fileUtil.Attachments;
import discordBot.bot.fileUtil.FileManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageLogic {
    static FileManager fileManager = new FileManager();
    private static CropImage cropImage = new CropImage();
    private Attachments attachments = new Attachments();
    private Compare compare = new Compare();

    /**
     * this will compare images
     * @param channel the specific channel that the message was sent in
     * @param message the specific message that was sent in a channel
     * @param main the bots main
     * @return returns true if the image comparison was completed without any issues or not
     */
    public boolean compareImage(MessageChannel channel, Message message, Bot main) {
        BufferedImage[] refs = fileManager.loadRefs(new File("Images/Downloaded/Refs/ref"));
        BufferedImage[] subImages;
        if (trySavingAttachment(channel,message)) {
             BufferedImage inputImg = fileManager.load(new File("Images/Downloaded/Input/inputRef.png"));
             cropImage.createSubImages(inputImg);
             subImages = fileManager.loadRefs(new File("Images/Downloaded/Input/ref"));
            boolean[] output = checkMatches(subImages,refs,0.06);
            return addItemToChannel(message,output,main);
        }
        return false;
    }

    /**
     * this returns a boolean array of matches
     * @param subImages the array of subImages that needs to be compared
     * @param refs the references the subImages will compare to
     * @param matchLimit the match threshold of how many % can be different
     * @return returns a boolean array of all the items, true if its a match and false if its not a match
     */
    private boolean[] checkMatches( BufferedImage[] subImages, BufferedImage[] refs, Double matchLimit) {
        boolean[] output = new boolean[5];
        for (int i=0; i < output.length;i++) {
            double temp = compare.findSubImageDouble(subImages[i],refs[i],matchLimit);
            if (temp < matchLimit) {
                output[i] = true;
                System.out.println("Match at "+i); // for debug purposes
            }
        }
        return output;
    }

    /**
     * this will try saving the attachment to the host computer
     * @param objChannel the specific channel
     * @param objMsg the specific message
     * @return true if saving was successful false if else
     */
    private boolean trySavingAttachment(MessageChannel objChannel, Message objMsg) {
        if (tryDeletingOldFile(new File("Images/Downloaded/Input/inputRef.PNG"))) {
            File b = attachments.downloadChangeName(objMsg, true, new File("Images/Downloaded/Input/inputRef.PNG"));
            if (b != null) {
                System.out.println("Saving worked! file has been saved!");
                return true;
            } else {
                objChannel.sendMessage("Collection of file failed or something went wrong").queue();
            }
        }
        return false;
    }

    /**
     * this will delete old file since saving attachment can't overwrite a file
     * @param file the filePath of the old inputRef
     * @return returns if the deletion of the file was successful
     */
    private boolean tryDeletingOldFile(File file) {
        try{
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * this will add all the true items in the boolean to the trading channel objects
     * @param message the specific message that triggered this function, it contains the channelObject callsign and id
     * @param output the boolean array of the matches from the comparison
     * @param main the bots main
     * @return true in all instances
     */
    private boolean addItemToChannel(Message message, boolean[] output, Bot main) {
        String[] emojiArray = new FileManager().loadStringArray(new File("Config/Variables/Items/TradingItems.txt"),true);
        String temp = message.getContentRaw().substring(0,3);
        ChannelManager channelManager = new ChannelManager();
        boolean b = false;
            for (int i = 0; i < output.length;i++) {
                if (output[i]) {
                    channelManager.getTradingChannelWithCallSignAndId(temp.substring(0,2).toLowerCase(),Integer.parseInt(String.valueOf(temp.charAt(2))),main).addItem(emojiArray[i], null);
                    b = true;
                }
            }
            if (b) {
                return true;
            } else {
                channelManager.getTradingChannelWithCallSignAndId(temp.substring(0,2).toLowerCase(),Integer.parseInt(String.valueOf(temp.charAt(2))),main).addItem("n", null);
                return true;
            }

    }
}
