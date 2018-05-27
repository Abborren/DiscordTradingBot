package discordBot.bot.botIO.output.ChannelHandling;


import discordBot.bot.Bot;
import discordBot.bot.fileUtil.FileManager;

import java.io.File;

public class ChannelManager {

    public void initiateTradingChannels(Bot main) {
        String[] channelCallSigns = new FileManager().loadStringArray(new File("Config/Variables/Channels/ChannelId.txt"));
        String[] channelName = new FileManager().loadStringArray(new File("Config/Variables/Channels/ChannelNames.txt"));

        for (int i = 0; i < channelName.length; i++) {
            for (int j = 1; j <= 6; j++) {
                if (channelName[i].equals(channelName[6]) && j >= 5 || channelName[i].equals(channelName[7]) && j >= 2) {
                    break;
                } else {
                    main.tradingChannelObjects.add(new TradingChannelObject(channelName[i],channelCallSigns[i],j));
                }
            }
        }
    }
    public TradingChannelObject getTradingChannelWithCallSignAndId(String channelCallSign, int id,Bot main) {
        for (TradingChannelObject tradingChannelObject : main.tradingChannelObjects) {
            if (tradingChannelObject.callSign.equalsIgnoreCase(channelCallSign) && tradingChannelObject.id == id) {
                return tradingChannelObject;
            }
        }
        return null;
    }
}
