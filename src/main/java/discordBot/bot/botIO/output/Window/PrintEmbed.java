package discordBot.bot.botIO.output.Window;

import discordBot.bot.Bot;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class PrintEmbed {
    /**
     * this intially prints the embed if the embed no-longer exists
     * @param main the bots main
     * @param channel the specified channel where the message is supposed to be sent in
     * @return returns the message id of the embed
     */
    public String printEmbed(Bot main, MessageChannel channel) {
        BuildEmbed buildEmbed = new BuildEmbed();
        MessageEmbed message = buildEmbed.buildOutput(main).build();
        return channel.sendMessage(message).complete().getId();
    }

    /**
     * this edits a existing embed
     * @param main the bots main
     * @param channel the specific messageChannel where the embed is in
     */
    public void editEmbed(Bot main, MessageChannel channel) {
        BuildEmbed buildEmbed = new BuildEmbed();
        channel.editMessageById(main.botMessageId,buildEmbed.buildOutput(main).build()).complete();
    }
}
