package discordBot.main.FileUtil.image;

import discordBot.main.FileUtil.Attachments;
import discordBot.main.FileUtil.FileManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageLogic {
    static FileManager fileManager = new FileManager();
    private static CropImage cropImage = new CropImage();
    private Attachments attachments = new Attachments();
    private Compare compare = new Compare();

    public void compareImage(MessageChannel channel,Message message) {
        BufferedImage[] refs = fileManager.loadRefs(new File("Images/Downloaded/Refs/ref"));
        BufferedImage[] subImages;
        if (trySavingAttachment(channel,message)) {
             BufferedImage inputImg = fileManager.load(new File("Images/Downloaded/Input/inputRef.png"));
             cropImage.createSubImages(inputImg);
             subImages = fileManager.loadRefs(new File("Images/Downloaded/Input/ref"));
            boolean[] output = checkMatches(new boolean[5],subImages,refs,0.05);
            StringBuilder temp = buildOutputMessage(message,channel,output);
            channel.sendMessage(temp).queue();
        }
    }
    public void compareImageTest() {
        BufferedImage[] refs = fileManager.loadRefs(new File("Images/Downloaded/Refs/ref"));
        BufferedImage[] subImages;

        BufferedImage inputImg = fileManager.load(new File("Images/Downloaded/Input/inputRef.png"));
        cropImage.createSubImages(inputImg);
        subImages = fileManager.loadRefs(new File("Images/Downloaded/Input/ref"));
        boolean[] output = checkMatches(new boolean[5],subImages,refs,0.05);
        for (int i = 0; i < 3; i++) {
            if (output[i]) {
                System.out.println("boolean " + i + " ist true!");
            }
        }
    }

    private boolean[] checkMatches(boolean[] output, BufferedImage[] subImages, BufferedImage[] refs, Double matchLimit) {
        for (int i=0; i < output.length;i++) {
            double temp = compare.findSubImageDouble(subImages[i],refs[i],matchLimit);
            if (temp < matchLimit) {
                System.out.println("match % "+temp);
                output[i] = true;
                System.out.println("Match at "+i);
            }
            else {
                System.out.println("No Match at " +i);
            }
        }
        return output;
    }

    private boolean trySavingAttachment(MessageChannel objChannel, Message objMsg) {
        if (tryDeletingOldFile(new File("Images/Downloaded/Input/inputRef.png"))) {
            File b = attachments.downloadChangeName(objMsg, true, new File("Images/Downloaded/Input/inputRef.png"));
            if (b != null) {
                objChannel.sendMessage("Saving worked! file has been saved!").queue();
                return true;
            } else {
                objChannel.sendMessage("Saving failed or something went wrong!").queue();
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
    private StringBuilder buildOutputMessage(Message message, MessageChannel channel, boolean[] output) {
        String[] emojiArray = {":lacquerware:",":lamp:",":spice:",":saber:",":kite:"};
        StringBuilder outputString;
        outputString = new StringBuilder();
        String temp = message.getContentRaw().substring(0,3);
                outputString.append(temp);
            for (int i = 0; i < emojiArray.length;i++) {
                if (output[i]) {
                    outputString.append(" ").append(emojiArray[i]);
                }
            }
            return outputString;
    }
}
