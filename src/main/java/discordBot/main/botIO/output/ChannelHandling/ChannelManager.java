package discordBot.main.botIO.output.ChannelHandling;


import discordBot.main.Bot;

public class ChannelManager {

    public void initiateTradingChannels(Bot main) {
        String[] channelCallSigns = {"ba","ve","se","me","ca","va","ka","ar"};
        String[] channelName = {"Balenos","Velia","Serendia","Mediah","Calpheon","Valencia","Kamasylvia","Arsha (PVP)"};

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
