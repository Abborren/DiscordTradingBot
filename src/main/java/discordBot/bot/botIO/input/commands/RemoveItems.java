package discordBot.bot.botIO.input.commands;

import discordBot.bot.Bot;
import discordBot.bot.botIO.output.ChannelHandling.ChannelManager;
import discordBot.bot.botIO.output.ChannelHandling.TradingChannelObject;
import discordBot.bot.botIO.output.Window.PrintEmbed;
import discordBot.bot.fileUtil.FileManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.File;

public class RemoveItems {
    private PrintEmbed printEmbed = new PrintEmbed();
    public void removeItems(Message objMsg, MessageChannel objChannel, Bot main) {
        Thread thread = new Thread(() -> {

            boolean removeAll = true;
            if (objMsg.getContentRaw().startsWith("!wipe") && objMsg.getContentRaw().contains(" ")) {

                String[] temp = objMsg.getContentRaw().substring(6).split(" ");
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = temp[i].toLowerCase();

                }
                for (String s : temp) {
                    if (s.startsWith("<:")) {
                        removeAll = false;
                    }
                }
                TradingInput tradingInput = new TradingInput();
                String[] items = tradingInput.returnRemoveItems(temp, removeAll);
                String[] channelCommandsArray = new FileManager().loadStringArray(new File("Config/Variables/Channels/ChannelId.txt"),false);
                int[] channelNumberArray = {1, 2, 3, 4, 5, 6};

                outerLoop:
                for (String channelCommand : channelCommandsArray) {
                    for (int number : channelNumberArray) {
                        //if channel limit is met it will break
                        if (channelCommand.equals("ka") && number < 4 || channelCommand.equals("ar") && number < 1) {
                            break outerLoop;
                        } else if (temp[0].startsWith(channelCommand) && temp[0].endsWith(String.valueOf(number))) {
                            ChannelManager channelManager = new ChannelManager();
                            TradingChannelObject tradingChannel = channelManager.getTradingChannelWithCallSignAndId(temp[0].substring(0, 2), Integer.parseInt(String.valueOf(temp[0].charAt(2))), main);
                            for (String item : items) {
                                tradingChannel.removeItem(item);
                            }
                            break outerLoop;
                        }
                    }
                }
                printEmbed.editEmbed(main, objChannel);
            }
        });
        thread.start();
    }
}
