package discordBot.main.outputWindow.Window;

import discordBot.main.App;
import discordBot.main.outputWindow.ChannelHandling.TradingChannelObject;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.ArrayList;

public class BuildEmbed {
    public EmbedBuilder buildOutput(App main) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        ArrayList<TradingChannelObject> tradingChannels = new ArrayList<TradingChannelObject>(main.tradingChannelObjects);
        embedBuilder.setTitle("Imperial Trade Delivery Status");
        ArrayList<MessageEmbed.Field> fieldArrayList = new ArrayList<MessageEmbed.Field>();

        for (TradingChannelObject tradingChannel : tradingChannels) {
            fieldArrayList.add(new MessageEmbed.Field(tradingChannel.name, getFieldInline(tradingChannel),true));

        }
        for (int i = 0; i < fieldArrayList.size() ;i++) {
            embedBuilder.addField(fieldArrayList.get(i));
        }
        return embedBuilder;
    }
    private String getFieldInline(TradingChannelObject tradingChannel) {
        //add channel
        StringBuilder returnS = new StringBuilder();

        returnS.append("\r\n").append(tradingChannel.id).append(" ");
        for (int j = 0; j < tradingChannel.items.length; j++) {
            if (tradingChannel.items[j][0] != null) {
                returnS.append(tradingChannel.items[j][0]).append(" ");
                if (!tradingChannel.items[j][1].equalsIgnoreCase("N/A")) {
                    returnS.append(tradingChannel.items[j][1]);
                }
            }
        }
        return returnS.toString();
    }
}
