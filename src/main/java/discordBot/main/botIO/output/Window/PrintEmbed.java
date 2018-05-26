package discordBot.main.botIO.output.Window;

import discordBot.main.Bot;
import net.dv8tion.jda.core.entities.MessageChannel;

public class PrintEmbed {
    public void printEmbed(Bot main, MessageChannel channel) {
        BuildEmbed buildEmbed = new BuildEmbed();
        channel.sendMessage(buildEmbed.buildOutput(main).build()).queue();
    }
}
