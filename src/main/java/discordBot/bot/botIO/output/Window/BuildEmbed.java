package discordBot.bot.botIO.output.Window;

import discordBot.bot.Bot;
import discordBot.bot.botIO.output.ChannelHandling.TradingChannelObject;
import discordBot.bot.fileUtil.FileManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class BuildEmbed {
    /**
     * Builds the whole embed message
     * @param main the Bots main
     * @return returns the embed to be printed
     */
    EmbedBuilder buildOutput(Bot main) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        ArrayList<TradingChannelObject> tradingChannels = new ArrayList<TradingChannelObject>(main.tradingChannelObjects);
        embedBuilder.setColor(0x00ff00);
        embedBuilder.addField("Imperial Trade Delivery Status",main.gameMessage,false);
        embedBuilder.addBlankField(false);
        ArrayList<MessageEmbed.Field> fieldArrayList = new ArrayList<MessageEmbed.Field>();
        int channelAmount = 0;
        int channelIndex = 0;
        String oldChannelCS = tradingChannels.get(0).callSign;
        for (int i=0; i < tradingChannels.size(); i++) {
            if (!tradingChannels.get(i).callSign.equals(oldChannelCS)) {
                fieldArrayList.add(new MessageEmbed.Field(tradingChannels.get(i-1).name, getFieldInline(tradingChannels.subList(channelIndex-(channelAmount),channelIndex),channelAmount),false));
                oldChannelCS = tradingChannels.get(i).callSign;
                channelAmount = 0;
            }
            channelIndex++;
            channelAmount++;
            if (i+1 == tradingChannels.size()) {
                fieldArrayList.add(new MessageEmbed.Field(tradingChannels.get(i).name, getFieldInline(tradingChannels.subList(channelIndex-(channelAmount),channelIndex),channelAmount),false));
            }
        }
        for (MessageEmbed.Field aFieldArrayList : fieldArrayList) {
            embedBuilder.addField(aFieldArrayList);
        }
        fieldArrayList.clear();
        return embedBuilder;
    }

    /**
     * creates all inlines
     * @param tradingChannels gets the tradingChannel objects array
     * @param channelGroupAmount the amount of channels in a group
     * @return returns the inline i a channel group
     */
    private String getFieldInline(List<TradingChannelObject> tradingChannels, int channelGroupAmount) {
        //add channel
        StringBuilder returnS = new StringBuilder();
        String[] notItems = new FileManager().loadStringArray(new File("Config/Variables/Items/TradingNotItems.txt"),false);
        for (int i = 0; i < channelGroupAmount; i++) {
            returnS.append("\r\n").append(tradingChannels.get(i).id).append(" ");
            boolean[] importantItems = {false,true,false,true,true,false,false,true,false,true};
            for (int j = 0; j < tradingChannels.get(i).items.length; j++) {
                if (tradingChannels.get(i).items[j][0] != null) {
                    if (tradingChannels.get(i).items[j][0].equals("n")){
                        returnS.append("Nothing here!");
                        break;
                    } else if (tradingChannels.get(i).items[j][1].equalsIgnoreCase("0")) {
                        returnS.append(" ").append(notItems[j]).append(" ");
                    } else {
                        if (!tradingChannels.get(i).items[j][1].equalsIgnoreCase("N/A")) {
                            importantItems[j] = true;
                        }
                        if (importantItems[j]) {
                            returnS.append(" ").append(tradingChannels.get(i).items[j][0]).append(" ");
                            if (!tradingChannels.get(i).items[j][1].equalsIgnoreCase("N/A")) {
                                returnS.append(tradingChannels.get(i).items[j][1]);
                            }
                        }

                    }
                }
            }
        }
        return returnS.toString();
    }
}
