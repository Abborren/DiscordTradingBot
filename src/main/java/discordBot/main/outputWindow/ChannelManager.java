package discordBot.main.outputWindow;


import java.util.ArrayList;

public class ChannelManager {
    private ArrayList<TradingChannel> tradingChannels = new ArrayList<TradingChannel>();
    public void initiateTradingChannels() {
        String[] channelCallSigns = {"ba","ve","se","me","ca","va","ka","ar"};
        String[] channelName = {"Balenos","Velia","Serendia","Mediah","Calpheon","Valencia","kamasylvia","Arsha_PVP"};
        int channelId = 0;
        for (int i = 0; i < channelName.length; i++) {
            for (int j = 1; j <= 6; j++) {
                if (channelName[i].equals(channelName[6]) && j >= 5 || channelName[i].equals(channelName[7]) && j >= 2) {
                    break;
                } else {
                    tradingChannels.add(new TradingChannel(channelName[i],channelCallSigns[i],j));
                    System.out.println(tradingChannels.get(channelId).name+" "+j+ "\r\n");
                    channelId++;
                }
            }
        }
    }
    public TradingChannel getTradingChannelWithNameAndId(String channelName,int id) {
        for (TradingChannel tradingChannel :tradingChannels) {
            if (tradingChannel.name.equalsIgnoreCase(channelName) && tradingChannel.id == id) {
                return tradingChannel;
            }
        }
        return null;
    }
    public void addItems(String s,TradingChannel tradingChannel) {
        String[][] itemPairs = {{"<:lacquerware:365925547563286528>","<:cencer:365926188968968222>"},
                {"<:lamp:365926091241816076>","<:ginseng:365926221122371586>"},
                {"<:spice:365926064116989972>","<:slab:365926150561726465>"},
                {"<:saber:365926042180911114>","<:porcelain:365925475630972928>"},
                {"<:kite:365926019254714369>","<:silk:365926117024202754>"}};
        outerLoop:
        for (int i = 0; i < itemPairs[0].length; i++) {
           for (int j = 0; j < 2; j++) {
               if (itemPairs[i][j].equalsIgnoreCase(s)) {
                    tradingChannel.items[i][0] = itemPairs[i][0];
                    tradingChannel.items[i][1] = itemPairs[i][1];
                    break outerLoop;
               }
           }
        }
    }
}
