package discordBot.main.outputWindow.ChannelHandling;


import discordBot.main.App;
import java.util.Arrays;

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
    public void addItem(String tradingItem, TradingChannelObject tradingChannelObject, String amount) {
        String[][] itemPairs = {{"<:lacquerware:365925547563286528>","<:cencer:365926188968968222>"},
                {"<:lamp:365926091241816076>","<:ginseng:365926221122371586>"},
                {"<:spice:365926064116989972>","<:slab:365926150561726465>"},
                {"<:saber:365926042180911114>","<:porcelain:365925475630972928>"},
                {"<:kite:365926019254714369>","<:silk:365926117024202754>"}};
        for (int i = 0; i < itemPairs.length; i++) {

               if (itemPairs[i][0].equalsIgnoreCase(tradingItem)) {
                    tradingChannelObject.items[((i+1)*2)-2][0] = itemPairs[i][0];
                    tradingChannelObject.items[((i+1)*2)-1][0] = itemPairs[i][1];
                    if (!amount.equals("N/A")) {
                        tradingChannelObject.items[i][1] = amount;
                    }
                    break;
               } else if (itemPairs[i][1].equalsIgnoreCase(tradingItem)) {
                   tradingChannelObject.items[((i+1)*2)-1][0] = itemPairs[i][0];
                   tradingChannelObject.items[((i+1)*2)-2][0] = itemPairs[i][1];
                   if (!amount.equals("N/A")) {
                       tradingChannelObject.items[i+1][1] = amount;
                   }
                   break;
               }
        }
        System.out.println("Channel now contains "+ Arrays.deepToString(tradingChannelObject.items));
    }
    public void removeItem(String tradingItem, TradingChannelObject tradingChannelObject, String amount) {
        String[][] itemPairs = {{"<:lacquerware:365925547563286528>","<:cencer:365926188968968222>"},
                {"<:lamp:365926091241816076>","<:ginseng:365926221122371586>"},
                {"<:spice:365926064116989972>","<:slab:365926150561726465>"},
                {"<:saber:365926042180911114>","<:porcelain:365925475630972928>"},
                {"<:kite:365926019254714369>","<:silk:365926117024202754>"}};
        for (int i = 0; i < itemPairs.length; i++) {

            if (itemPairs[i][0].equalsIgnoreCase(tradingItem) || itemPairs[i][1].equalsIgnoreCase(tradingItem)) {
                tradingChannelObject.items[((i+1)*2)-2][0] = null;
                tradingChannelObject.items[((i+1)*2)-1][0] = null;
                if (!tradingChannelObject.items[i][1].equals("N/A")) {
                    tradingChannelObject.items[i][1] = "N/A";
                }
                break;
            }
        }
        System.out.println("Channel now contains "+ Arrays.deepToString(tradingChannelObject.items));
    }
    public void clearTradingChannels(App main) {
        main.tradingChannelObjects.clear();
    }
}
