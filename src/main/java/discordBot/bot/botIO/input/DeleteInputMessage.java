package discordBot.bot.botIO.input;

import net.dv8tion.jda.core.entities.MessageChannel;

public class DeleteInputMessage {
    /**
     * this deletes the latest sent message in a specific channel mostly in the same channel that the message was sent in.
     * @param channel the specific channel where this function was triggered from
     */
    public void deleteMessage(MessageChannel channel) {
        String id = channel.getLatestMessageId();
        //System.out.println(id); // debug feature to check what message is deleted
        channel.deleteMessageById(id).complete();
    }
}
