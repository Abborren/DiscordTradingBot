package discordBot.main.botIO.input;

import net.dv8tion.jda.core.entities.MessageChannel;

public class DeleteIputMessage {
    public void deleteMessage(MessageChannel channel) {
        String id = channel.getLatestMessageId();
        System.out.println(id);
        channel.deleteMessageById(id).complete();
    }
}
