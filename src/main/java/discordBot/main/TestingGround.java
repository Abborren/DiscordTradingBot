package discordBot.main;

import discordBot.main.outputWindow.ChannelManager;

public class TestingGround {
    public static void main(String[] args) {
        ChannelManager channelManager = new ChannelManager();
        channelManager.initiateTradingChannels();
        channelManager.addItems("<:lacquerware:365925547563286528>",channelManager.getTradingChannelWithNameAndId("Balenos",1));
    }
}
