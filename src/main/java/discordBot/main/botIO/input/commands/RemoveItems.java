package discordBot.main.botIO.input.commands;

import discordBot.main.Bot;
import discordBot.main.botIO.input.TradingInput;
import discordBot.main.botIO.output.ChannelHandling.TradingChannelObject;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class RemoveItems {
    public void removeItems(Message objMsg, MessageChannel objChannel, Bot main) {
        boolean removeAll = true;
        if (objMsg.getContentRaw().startsWith("!wipe") && objMsg.getContentRaw().contains(" ")) {

            String[] temp = objMsg.getContentRaw().substring(6).split(" ");
            for (int i = 0; i < temp.length; i++) {
                temp[i] = temp[i].toLowerCase();

            }
            for (String s: temp) {
                if (s.startsWith("<:")) {
                    removeAll = false;
                }
            }
            TradingInput tradingInput = new TradingInput();
            String[] items = tradingInput.returnRemoveItems(temp,removeAll);
            String[] channelCommandsArray = {"ba", "ve", "se", "me", "ca", "va", "ka", "ar"};
            int[] channelNumberArray = {1, 2, 3, 4, 5, 6};

            outerLoop:
            for (String channelCommand : channelCommandsArray) {
                for (int number : channelNumberArray) {
                    //if channel limit is met it will break
                    if (channelCommand.equals("ka") && number < 4 || channelCommand.equals("ar") && number < 1) {
                        break outerLoop;
                    } else if (temp[0].startsWith(channelCommand) && temp[0].endsWith(String.valueOf(number))) {
                        TradingChannelObject tradingChannel = main.channelManager.getTradingChannelWithCallSignAndId(temp[0].substring(0, 2), Integer.parseInt(String.valueOf(temp[0].charAt(2))), main);
                        for (String item : items) {
                            tradingChannel.removeItem(item);
                        }
                        break outerLoop;
                    }
                }
            }
            main.printEmbed.editEmbed(main,objChannel);
        }
    }
}
