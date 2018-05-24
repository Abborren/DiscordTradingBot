package discordBot.main.outputWindow;


import java.util.ArrayList;
import java.util.Arrays;

public class ChannelManager {
    public ArrayList<TradingChannelObject> tradingChannelObjects = new ArrayList<TradingChannelObject>();
    public void initiateTradingChannels() {
        String[] channelCallSigns = {"ba","ve","se","me","ca","va","ka","ar"};
        String[] channelName = {"Balenos","Velia","Serendia","Mediah","Calpheon","Valencia","kamasylvia","Arsha_PVP"};
        int channelId = 0;
        for (int i = 0; i < channelName.length; i++) {
            for (int j = 1; j <= 6; j++) {
                if (channelName[i].equals(channelName[6]) && j >= 5 || channelName[i].equals(channelName[7]) && j >= 2) {
                    break;
                } else {
                    tradingChannelObjects.add(new TradingChannelObject(channelName[i],channelCallSigns[i],j));
                    System.out.println(tradingChannelObjects.get(channelId).name+" "+j+ "\r\n");
                    channelId++;
                }
            }
        }
    }
    public TradingChannelObject getTradingChannelWithNameAndId(String channelName, int id) {
        for (TradingChannelObject tradingChannelObject : tradingChannelObjects) {
            if (tradingChannelObject.name.equalsIgnoreCase(channelName) && tradingChannelObject.id == id) {
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
        System.out.println("before change "+ Arrays.deepToString(tradingChannelObject.items));
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
}
