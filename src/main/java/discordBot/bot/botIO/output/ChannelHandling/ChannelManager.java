package discordBot.bot.botIO.output.ChannelHandling;


import discordBot.bot.Bot;
import discordBot.bot.fileUtil.FileManager;

import java.io.File;

public class ChannelManager {
    /**
     * this initially creates the trading channel objects when the bot starts
     * @param main is the Bots main and is where the tradingChannel objects are stored
     */
    public void initiateTradingChannels(Bot main) {
        String[] channelCallSigns = new FileManager().loadStringArray(new File("Config/Variables/Channels/ChannelCallSign.txt"),false);
        String[] channelName = new FileManager().loadStringArray(new File("Config/Variables/Channels/ChannelNames.txt"),false);

        for (int i = 0; i < channelName.length; i++) {
            for (int j = 1; j <= 6; j++) {
                if (channelName[i].equals("Kamasylvia") && j > 4 || channelName[i].equals("Drieghan") && j > 4 || channelName[i].equals("Arsha (PVP)") && j > 1) {
                    break;
                } else {
                    main.tradingChannelObjects.add(new TradingChannelObject(channelName[i],channelCallSigns[i],j));
                }
            }
        }
    }

    /**
     * gets by callsign and id the right trading channel object
     * @param channelCallSign the callsign of the channel for example va or ba
     * @param id a number between 1 and 6 for the most part
     * @param main the bots main
     * @return returns the correct channel if it exists if not returns null
     */
    public TradingChannelObject getTradingChannelWithCallSignAndId(String channelCallSign, int id,Bot main) {
        for (TradingChannelObject tradingChannelObject : main.tradingChannelObjects) {
            if (tradingChannelObject.callSign.equalsIgnoreCase(channelCallSign) && tradingChannelObject.id == id) {
                return tradingChannelObject;
            }
        }
        return null;
    }
}
