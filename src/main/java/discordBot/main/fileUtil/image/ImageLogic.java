package discordBot.main.fileUtil.image;

import discordBot.main.Bot;
import discordBot.main.fileUtil.Attachments;
import discordBot.main.fileUtil.FileManager;
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
        if (tryDeletingOldFile(new File("Images/Downloaded/Input/inputRef.png"))) {
            File b = attachments.downloadChangeName(objMsg, true, new File("Images/Downloaded/Input/inputRef.png"));
            if (b != null) {
                //System.out.println("Saving worked! file has been saved!");
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
        String[] emojiArray = {"<:lacquerware:365925547563286528>","<:lamp:365926091241816076>","<:spice:365926064116989972>","<:saber:365926042180911114>","<:kite:365926019254714369>"};
        String temp = message.getContentRaw().substring(0,3);
            for (int i = 0; i < output.length;i++) {
                if (output[i]) {
                    main.channelManager.getTradingChannelWithCallSignAndId(temp.substring(0,2).toLowerCase(),Integer.parseInt(String.valueOf(temp.charAt(2))),main).addItem(emojiArray[i], null);
                }
            }
            return true;
    }
}
