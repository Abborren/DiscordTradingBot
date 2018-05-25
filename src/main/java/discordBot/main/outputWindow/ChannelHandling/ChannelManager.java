package discordBot.main.outputWindow.ChannelHandling;


import discordBot.main.App;

public class ChannelManager {

    public void initiateTradingChannels(App main) {
        String[] channelCallSigns = {"ba","ve","se","me","ca","va","ka","ar"};
        String[] channelName = {"Balenos","Velia","Serendia","Mediah","Calpheon","Valencia","Kamasylvia","Arsha (PVP)"};
        int channelId = 0;
        for (int i = 0; i < channelName.length; i++) {
            for (int j = 1; j <= 6; j++) {
                if (channelName[i].equals(channelName[6]) && j >= 5 || channelName[i].equals(channelName[7]) && j >= 2) {
                    break;
                } else {
                    main.tradingChannelObjects.add(new TradingChannelObject(channelName[i],channelCallSigns[i],j));
                    System.out.println(main.tradingChannelObjects.get(channelId).callSign +" "+main.tradingChannelObjects.get(channelId).id);
                    channelId++;
                }
            }
        }
    }
    public TradingChannelObject getTradingChannelWithCallSignAndId(String channelCallSign, int id,App main) {
        for (TradingChannelObject tradingChannelObject : main.tradingChannelObjects) {
            if (tradingChannelObject.callSign.equalsIgnoreCase(channelCallSign) && tradingChannelObject.id == id) {
                return tradingChannelObject;
            }
        }
        return null;
    }
    public void clearTradingChannels(App main) {
        main.tradingChannelObjects.clear();
    }
}
