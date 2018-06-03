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

    public boolean compareImage(MessageChannel channel, Message message, Bot main) {
        BufferedImage[] refs = fileManager.loadRefs(new File("Images/Downloaded/Refs/ref"));
        BufferedImage[] subImages;
        if (trySavingAttachment(channel,message)) {
             BufferedImage inputImg = fileManager.load(new File("Images/Downloaded/Input/inputRef.png"));
             cropImage.createSubImages(inputImg);
             subImages = fileManager.loadRefs(new File("Images/Downloaded/Input/ref"));
            boolean[] output = checkMatches(new boolean[5],subImages,refs,0.05);
            return addItemToChannel(message,output,main);
        }
        return false;
    }

    private boolean[] checkMatches(boolean[] output, BufferedImage[] subImages, BufferedImage[] refs, Double matchLimit) {
        for (int i=0; i < output.length;i++) {
            double temp = compare.findSubImageDouble(subImages[i],refs[i],matchLimit);
            if (temp < matchLimit) {
                output[i] = true;
                System.out.println("Match at "+i); // for debug purposes
            }
        }
        return output;
    }

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
