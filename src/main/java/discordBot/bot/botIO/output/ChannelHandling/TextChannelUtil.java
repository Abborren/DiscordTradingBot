package discordBot.bot.botIO.output.ChannelHandling;
import discordBot.bot.Bot;
import net.dv8tion.jda.core.entities.TextChannel;

public class TextChannelUtil {

    /**
     * This will try to find a channel with the same name as @param s.
     * @param s input string, the channel searched for
     * @return TextChannel object if there is a match
     * @throws Exception if the channel is not found
     */
    public TextChannel getTextChannelByName(final String s) throws Exception {
        for (TextChannel textChannel: Bot.getTextChannels()) {

            String channelName = textChannel.getName();
            if (channelName.equalsIgnoreCase(s)) {
                return textChannel;
            }
        }
        throw new Exception("Channel does not exist");
    }

}

