package discordBot.bot.botIO.output;

import discordBot.bot.botIO.output.ChannelHandling.TextChannelUtil;
import discordBot.bot.fileUtil.FileManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;

public class ForwardMessage {
    public void forwardMessage(MessageChannel thisChannel, User thisUser, Message thisMsg) {
        try {
            TextChannel textChannel = new TextChannelUtil().getTextChannelByName(new FileManager().loadString(new File("Config/Variables/Channels/TradingHistoryChannel.txt")));
            if (thisMsg.getContentRaw().startsWith("!wipe")) {
                textChannel.sendMessage(thisUser.getAsMention() + " Removed reports with: " + thisMsg.getContentRaw()).queue();
            } else {
                textChannel.sendMessage(thisUser.getAsMention() + " Reported: "+ thisMsg.getContentRaw()).queue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
