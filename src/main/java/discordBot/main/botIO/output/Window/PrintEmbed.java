package discordBot.main.botIO.output.Window;

import discordBot.main.Bot;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class PrintEmbed {
    public String printEmbed(Bot main, MessageChannel channel) {
        BuildEmbed buildEmbed = new BuildEmbed();
        MessageEmbed message = buildEmbed.buildOutput(main).build();
        return channel.sendMessage(message).complete().getId();
    }
    public void editEmbed(Bot main, MessageChannel channel) {
        BuildEmbed buildEmbed = new BuildEmbed();
        channel.editMessageById(main.botMessageId,buildEmbed.buildOutput(main).build()).complete();
    }
}
