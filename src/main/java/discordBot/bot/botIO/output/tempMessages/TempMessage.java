package discordBot.bot.botIO.output.tempMessages;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class TempMessage {
    /**
     *  will print a temporary message
     * @param channel the specifed channel where the message will be sent
     * @param messageBuilder the specific message that is to be sent
     * @param millis how many millis the message will be visible for before being deleted
     */
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
