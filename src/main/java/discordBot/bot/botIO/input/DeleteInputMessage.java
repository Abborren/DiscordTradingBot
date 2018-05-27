package discordBot.bot.botIO.input;

import net.dv8tion.jda.core.entities.MessageChannel;

public class DeleteInputMessage {
    public void deleteMessage(MessageChannel channel) {
        String id = channel.getLatestMessageId();
        //System.out.println(id); // debug feature to check what message is deleted
        channel.deleteMessageById(id).complete();
    }
}
