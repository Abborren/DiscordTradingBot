package discordBot.main.outputWindow;


import discordBot.main.App;
import java.util.Arrays;

public class ChannelManager {

    public void initiateTradingChannels(App main) {
        String[] channelCallSigns = {"ba","ve","se","me","ca","va","ka","ar"};
        String[] channelName = {"Balenos","Velia","Serendia","Mediah","Calpheon","Valencia","kamasylvia","Arsha_PVP"};
        int channelId = 0;
        for (int i = 0; i < channelName.length; i++) {
            for (int j = 1; j <= 6; j++) {
                if (channelName[i].equals(channelName[6]) && j >= 5 || channelName[i].equals(channelName[7]) && j >= 2) {
                    break;
                } else {
                    main.tradingChannelObjects.add(new TradingChannelObject(channelName[i],channelCallSigns[i],j));
                    System.out.println(main.tradingChannelObjects.get(channelId).callSign +" "+main.tradingChannelObjects.get(channelId).id+ "\r\n");
                    channelId++;
                }
            }
        }
    }
    public TradingChannelObject getTradingChannelWithCallSignAndId(String channelCallSign, int id,App main) {
        for (TradingChannelObject tradingChannelObject : main.tradingChannelObjects) {
            if (tradingChannelObject.callSign.equalsIgnoreCase(channelCallSign) && tradingChannelObject.id == id) {
                System.out.println("Trading channel found called"+tradingChannelObject.name+tradingChannelObject.id);
                return tradingChannelObject;
            }
        }
        return null;
    }
    public void addItems(String s, TradingChannelObject tradingChannelObject, String amount) {
        String[][] itemPairs = {{"<:lacquerware:365925547563286528>","<:cencer:365926188968968222>"},
                {"<:lamp:365926091241816076>","<:ginseng:365926221122371586>"},
                {"<:spice:365926064116989972>","<:slab:365926150561726465>"},
                {"<:saber:365926042180911114>","<:porcelain:365925475630972928>"},
                {"<:kite:365926019254714369>","<:silk:365926117024202754>"}};
        //System.out.println("before change "+ Arrays.deepToString(tradingChannelObject.items));
        outerLoop:
        for (int i = 0; i < itemPairs[0].length; i++) {
           for (int j = 0; j < 2; j++) {
               if (itemPairs[i][j].equalsIgnoreCase(s)) {
                    tradingChannelObject.items[i][0] = itemPairs[i][0];
                    tradingChannelObject.items[i+1][0] = itemPairs[i][1];
                    if (amount != null) {
                        tradingChannelObject.items[i][1] = amount;
                    }
                    break outerLoop;
               }
           }
        }
        System.out.println("after change "+ Arrays.deepToString(tradingChannelObject.items));
    }
    public void clearTradingChannels(App main) {
        main.tradingChannelObjects.clear();
        initiateTradingChannels(main);
    }
}
