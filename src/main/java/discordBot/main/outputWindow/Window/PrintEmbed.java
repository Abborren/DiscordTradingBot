package discordBot.main.outputWindow.Window;

import discordBot.main.App;
import net.dv8tion.jda.core.entities.MessageChannel;

public class PrintEmbed {
    public void printEmbed(App main, MessageChannel channel) {
        BuildEmbed buildEmbed = new BuildEmbed();
        channel.sendMessage(buildEmbed.buildOutput(main).build()).queue();
    }
}
