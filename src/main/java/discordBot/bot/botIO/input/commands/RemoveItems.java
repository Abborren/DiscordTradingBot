package discordBot.bot.botIO.input.commands;

import discordBot.bot.Bot;
import discordBot.bot.botIO.output.ChannelHandling.ChannelManager;
import discordBot.bot.botIO.output.ChannelHandling.TradingChannelObject;
import discordBot.bot.botIO.output.Window.PrintEmbed;
import discordBot.bot.fileUtil.FileManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.File;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Arrays;

public class RemoveItems {
    private PrintEmbed printEmbed = new PrintEmbed();
    /**
     * this removes items to a specific channel object.
     * @param objMsg the discord message in question
     * @param objChannel the discord message channel in question
     * @param main the main class of the bot, containing the JDA
     */
    public void removeItems(Message objMsg, MessageChannel objChannel, Bot main) {
        Thread thread = new Thread(() -> {

            boolean removeAll = true;
            if (objMsg.getContentRaw().startsWith("!wipe") && objMsg.getContentRaw().contains(" ") && !objMsg.getContentRaw().contains("all")) {

                String[] temp = objMsg.getContentRaw().substring(6).split(" ");
                // this will filter out empty strings in the array. aka it deals with double spaces
                temp = Arrays.stream(temp).filter(s -> !s.isEmpty()).toArray(String[]::new);
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
                String[] channelCommandsArray = new FileManager().loadStringArray(new File("Config/Variables/Channels/ChannelCallSign.txt"),false);
                int[] channelNumberArray = {1, 2, 3, 4, 5, 6};

                outerLoop:
                for (String channelCommand : channelCommandsArray) {
                    for (int number : channelNumberArray) {
                        //if channel limit is met it will break
                        if (channelCommand.startsWith("dr") && number >= 5 || channelCommand.startsWith("ka") && number >= 5 || channelCommand.startsWith("ar") && number >= 2) {
                            break outerLoop;

                        } else if (temp[0].startsWith(channelCommand) && temp[0].endsWith(String.valueOf(number))) {
                            ChannelManager channelManager = new ChannelManager();
                            TradingChannelObject tradingChannel;
                            if (temp[0].startsWith("v")) {
                                tradingChannel = channelManager.getTradingChannelWithCallSignAndId(temp[0].substring(0, 2), Integer.parseInt(String.valueOf(temp[0].charAt(temp[0].length()-1))), main);
                            } else {
                                tradingChannel = channelManager.getTradingChannelWithCallSignAndId(temp[0].substring(0, 1), Integer.parseInt(String.valueOf(temp[0].charAt(temp[0].length()-1))), main);
                            }

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
    public void resetManualAllItems(Message objMsg, MessageChannel objChannel, Bot main) {
        Thread thread = new Thread(() -> {
            if (objMsg.getContentRaw().startsWith("!wipe") && objMsg.getContentRaw().contains(" ") && objMsg.getContentRaw().contains("all")) {
                System.out.println("User "+ objMsg.getAuthor().getName() + " used manual reset at "+ LocalDate.now(Clock.systemUTC()));

                String[] temp = objMsg.getContentRaw().substring(6).split(" ");
                // this will filter out empty strings in the array. aka it deals with double spaces
                temp = Arrays.stream(temp).filter(s -> !s.isEmpty()).toArray(String[]::new);
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = temp[i].toLowerCase();

                }
                for (int i = 0; i < main.tradingChannelObjects.size(); i++) {
                    String[] items = new TradingInput().returnRemoveItems(temp, true);
                    for (String item : items) {
                        main.tradingChannelObjects.get(i).removeItem(item);
                    }
                }
                printEmbed.editEmbed(main, objChannel);
            }
        });
        thread.start();
    }
}
