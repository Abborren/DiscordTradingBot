package discordBot.bot.botIO.output.tempMessages;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class TempMessage {
    public void printTempMessage (MessageChannel channel, MessageBuilder messageBuilder, int millis) {
        String id = channel.sendMessage(messageBuilder.build()).complete().getId();
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.deleteMessageById(id).complete();
    }
}
