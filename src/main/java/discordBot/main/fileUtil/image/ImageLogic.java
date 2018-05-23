package discordBot.main.fileUtil.image;

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

    public void compareImage(MessageChannel channel,Message message) {
        BufferedImage[] refs = fileManager.loadRefs(new File("Images/Downloaded/Refs/ref"));
        BufferedImage[] subImages;
        if (trySavingAttachment(channel,message)) {
             BufferedImage inputImg = fileManager.load(new File("Images/Downloaded/Input/inputRef.png"));
             cropImage.createSubImages(inputImg);
             subImages = fileManager.loadRefs(new File("Images/Downloaded/Input/ref"));
            boolean[] output = checkMatches(new boolean[5],subImages,refs,0.05);
            StringBuilder temp = buildOutputMessage(message,output);
            channel.sendMessage(temp).queue();
        }
    }

    private boolean[] checkMatches(boolean[] output, BufferedImage[] subImages, BufferedImage[] refs, Double matchLimit) {
        for (int i=0; i < output.length;i++) {
            double temp = compare.findSubImageDouble(subImages[i],refs[i],matchLimit);
            if (temp < matchLimit) {
                //System.out.println("match % "+temp);
                output[i] = true;
                //System.out.println("Match at "+i);
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
    private StringBuilder buildOutputMessage(Message message, boolean[] output) {
        String[] emojiArray = {"<:lacquerware:448213627405860864>","<:lamp:448213947984904202>","<:spice:448214072945541130>","<:saber:448402763509006343","<:kite:448399613683302411>"};
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
