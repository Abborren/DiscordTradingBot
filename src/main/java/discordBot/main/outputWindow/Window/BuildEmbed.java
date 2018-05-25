package discordBot.main.outputWindow.Window;

import discordBot.main.App;
import discordBot.main.outputWindow.ChannelHandling.TradingChannelObject;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.List;

public class BuildEmbed {
    public EmbedBuilder buildOutput(App main) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        ArrayList<TradingChannelObject> tradingChannels = new ArrayList<TradingChannelObject>(main.tradingChannelObjects);
        embedBuilder.setTitle("Imperial Trade Delivery Status");
        ArrayList<MessageEmbed.Field> fieldArrayList = new ArrayList<MessageEmbed.Field>();
        int channelAmount = 0;
        int channelIndex = 0;
        String oldChannelCS = tradingChannels.get(0).callSign;
        for (int i=0; i < tradingChannels.size(); i++) {
            if (!tradingChannels.get(i).callSign.equals(oldChannelCS)) {
                fieldArrayList.add(new MessageEmbed.Field(tradingChannels.get(i-1).name, getFieldInline(tradingChannels.subList(channelIndex-(channelAmount),channelIndex),channelAmount),false));
                oldChannelCS = tradingChannels.get(i).callSign;
                channelAmount = 0;
            }
            channelIndex++;
            channelAmount++;
            if (i+1 == tradingChannels.size()) {
                fieldArrayList.add(new MessageEmbed.Field(tradingChannels.get(i).name, getFieldInline(tradingChannels.subList(channelIndex-(channelAmount),channelIndex),channelAmount),false));
            }
        }
        for (int i = 0; i < fieldArrayList.size() ;i++) {
            embedBuilder.addField(fieldArrayList.get(i));
        }
        fieldArrayList.clear();
        return embedBuilder;
    }
    private String getFieldInline(List<TradingChannelObject> tradingChannels, int channelGroupAmount) {
        //add channel
        StringBuilder returnS = new StringBuilder();

        for (int i = 0; i < channelGroupAmount; i++) {
            returnS.append("\r\n").append(tradingChannels.get(i).id).append(" ");
            for (int j = 0; j < tradingChannels.get(i).items.length; j++) {

                if (tradingChannels.get(i).items[j][0] != null) {
                    returnS.append(tradingChannels.get(i).items[j][0]).append(" ");
                    if (!tradingChannels.get(i).items[j][1].equalsIgnoreCase("N/A")) {
                        returnS.append(tradingChannels.get(i).items[j][1]);
                    }
                }
            }
        }
        return returnS.toString();
    }
}
