package discordBot.main.outputWindow.Window;

import discordBot.main.App;
import discordBot.main.outputWindow.ChannelHandling.TradingChannelObject;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.ArrayList;

public class Build {
    public void buildOutput(App main) {
        // TODO look up embedded messages and how building of them work
        ArrayList<TradingChannelObject> tradingChannels = new ArrayList<TradingChannelObject>();
        tradingChannels.addAll(main.tradingChannelObjects)
        for (TradingChannelObject tradingChannel : tradingChannels) {
            if (tradingChannel.id == 1) {
                System.out.println(tradingChannel.name);
                //adds title for channel group needs ln
            }
            //add channel
            System.out.print("\r\n" + tradingChannel.id + " ");
            for (int j = 0; j < tradingChannel.items.length) {
                if (tradingChannel.items[j][0] != null) {
                    System.out.print(tradingChannel.items[j][0] + " ");
                    if (!tradingChannel.items[j][1].equalsIgnoreCase("N/A")) {
                        System.out.print(tradingChannel.items[j][1]);
                    }
                }
            }
        }
    }
}
