package discordBot.main.FileUtil;

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
    public File downloadChangeName(Message message, boolean filterImages, File file) {
        for(Message.Attachment attachment : message.getAttachments()) {
            if(!filterImages || attachment.isImage()) {
                attachment.download(file);
                return file;
            }
        }
        return null;
    }
}
