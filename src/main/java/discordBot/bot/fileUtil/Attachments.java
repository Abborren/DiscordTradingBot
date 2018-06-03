package discordBot.bot.fileUtil;

import net.dv8tion.jda.core.entities.Message;

import java.io.File;

public class Attachments {


    /** function tris to downloadRetainName a attached file from discord
     * @param message inputted message that might contain a attachment
     * @param filterImages decides if it only wants to save images or if any file is fine
     * @return returns the file path that the image was saved to*/
    public File downloadRetainName(Message message, boolean filterImages, String directory) {
        for(Message.Attachment attachment : message.getAttachments()) {
            if(!filterImages || attachment.isImage()) {
                File file = new File(directory + attachment.getFileName());
                attachment.download(file);
                return file;
            }
        }
        return null;
    }

    /**
     *
     * @param message inptted message that might contain attachment
     * @param filterImages decides if function will only save images
     * @param file file path/name that the function will save file as.
     * @return returns the filepath that the file was saved to.
     */
    public File downloadChangeName(Message message, boolean filterImages, File file) {
        for(Message.Attachment attachment : message.getAttachments()) {
            if(!filterImages || attachment.isImage()) {
                if (attachment.download(file)) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * this checks if a message has a attachment
     * @param message the specific message that triggered this function
     * @return returns true if the message has a attachment if else returns false
     */
    public boolean CheckForAttachments(Message message) {
        //return message.getAttachments() != null;
        for(Message.Attachment attachment : message.getAttachments()) {
            if(attachment.isImage()) {
                return true;
            }
        }
        return false;
    }
}
