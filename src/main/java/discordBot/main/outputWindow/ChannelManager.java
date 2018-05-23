package discordBot.main.outputWindow;


import java.util.ArrayList;

public class ChannelManager {
    ArrayList<TradingChannel> tradingChannels = new ArrayList<TradingChannel>();
    public void initiateTradingChannels() {
        String[] channelCallSigns = {"ba","ve","se","me","ca","va","ka","ar"};
        String[] channelName = {"Balenos","Velia","Serendia","Mediah","Calpheon","Valencia","kamasylvia","Arsha_PVP"};
        for (int i = 0; i < channelName.length; i++) {
            tradingChannels.add(new TradingChannel(channelName[i],channelCallSigns[i]));
        }
        for (TradingChannel tradingChannel : tradingChannels) {
            if (tradingChannel.name.equals("Balenos")) {
                System.out.println("Balenos exists");
            }
            System.out.println(tradingChannel.name + "\r\n");
        }
    }
    private int tradingChannelAt(String s) {
        for (int i = 0; i < tradingChannels.size(); i++) {
            if (tradingChannels.get(i).name.equals(s)) {
                return i;
            }
        }
        return Integer.parseInt(null);
    }
    public void addItems(String s) {
        String[][] itemPairs = {{"<:lacquerware:365925547563286528>","<:cencer:365926188968968222>"},
                {"<:lamp:365926091241816076>","<:ginseng:365926221122371586>"},
                {"<:spice:365926064116989972>","<:slab:365926150561726465>"},
                {"<:saber:365926042180911114>","<:porcelain:365925475630972928>"},
                {"<:kite:365926019254714369>","<:silk:365926117024202754>"}};

    }
}
