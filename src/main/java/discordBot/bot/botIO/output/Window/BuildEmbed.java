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
        ArrayList<TradingChannelObject> tradingChannels = new ArrayList<>(main.tradingChannelObjects);
        embedBuilder.setColor(0x00ff00);
        embedBuilder.addField("Imperial Trade Delivery Status",main.gameMessage,false);
        embedBuilder.addBlankField(false);
        ArrayList<MessageEmbed.Field> fieldArrayList = new ArrayList<MessageEmbed.Field>();
        List<TradingChannelObject> tradingChannelsSub;

        int channelAmount = 0;
        int channelIndex = 0;
        String oldChannelCS = tradingChannels.get(0).callSign;
        for (int i = 0; i < tradingChannels.size(); i++) {
            if (!tradingChannels.get(i).callSign.equals(oldChannelCS )) {
                tradingChannelsSub = getTradingChannelSubList(tradingChannels, channelAmount, channelIndex);

                String fieldInline = getFieldInline(tradingChannelsSub, channelAmount);
                String header = createHeader(tradingChannels.get(i-1).name, tradingChannelsSub);

                fieldArrayList.add(new MessageEmbed.Field(header, fieldInline,false));
                oldChannelCS = tradingChannels.get(i).callSign;
                channelAmount = 0;
            }
            channelIndex++;
            channelAmount++;
            if (i+1 == tradingChannels.size()) {
                tradingChannelsSub = getTradingChannelSubList(tradingChannels, channelAmount, channelIndex);
                String fieldInline = getFieldInline(tradingChannelsSub, channelAmount);

                String header = createHeader(tradingChannels.get(i).name, tradingChannelsSub);

                fieldArrayList.add(new MessageEmbed.Field(header, fieldInline,false));
            }
        }
        for (MessageEmbed.Field aFieldArrayList : fieldArrayList) {
            embedBuilder.addField(aFieldArrayList);
        }
        fieldArrayList.clear();
        return embedBuilder;
    }

    /**
     * creates a sublist out of trading a list of tradingChannelObjects
     * @param tradingChannels the input list
     * @param channelAmount the channel
     * @param channelIndex the start index - channelamount is the start of the list and channelIndex is the end of the list
     * @return returns a sublist of TradingChannelObjects
     */
    private List<TradingChannelObject> getTradingChannelSubList(ArrayList<TradingChannelObject> tradingChannels, int channelAmount, int channelIndex) {
        List<TradingChannelObject> tradingChannelsSub;
        tradingChannelsSub = tradingChannels.subList(channelIndex - channelAmount,channelIndex);
        return tradingChannelsSub;
    }

    /**
     *  Creates a channel group header
     * @param channelName the channel name
     * @param tradingChannelObjectList SubList of tradingChannels
     * @return returns a header;
     */
    private String createHeader(String channelName,List<TradingChannelObject> tradingChannelObjectList) {
        String s = getChannelNeedingInfo(tradingChannelObjectList);
        if (s.equals("()")) {
            return channelName;
        } else {
            return channelName + ": " + s;
        }
    }

    /**
     *
     * @param tradingChannels the channel group needing to be checked for info
     * @return returns a list of channels in a group that is missing information
     */
    private String getChannelNeedingInfo(List<TradingChannelObject> tradingChannels) {
        StringBuilder returnStatement = new StringBuilder();
        returnStatement.append("(");
        for (int i = 0; i < tradingChannels.size(); i++) {
            if (!channelHasItems(tradingChannels.get(i))) {
                returnStatement.append(i+1).append(",");

            }
        }
        if (returnStatement.length() >= 2) returnStatement.deleteCharAt(returnStatement.length()-1);

        returnStatement.append(")");
        return returnStatement.toString();
    }

    /**
     * creates all inlines
     * @param tradingChannels gets the tradingChannel objects array
     * @param channelGroupAmount the amount of channels in a group
     * @return returns the inline i a channel group
     */
    private String getFieldInline(List<TradingChannelObject> tradingChannels, int channelGroupAmount) {

        StringBuilder returnString = new StringBuilder();
        String[] notItems = new FileManager().loadStringArray(new File("Config/Variables/Items/TradingNotItems.txt"),false);
        for (int i = 0; i < channelGroupAmount; i++) {
            if (channelHasItems(tradingChannels.get(i))) {

                returnString.append("\r\n").append(tradingChannels.get(i).id).append(": ");

                boolean[] importantItems = {false,true,false,true,true,false,false,true,false,true};

                for (int j = 0; j < tradingChannels.get(i).items.length; j++) {
                    if (tradingChannels.get(i).items[j][0] != null) {

                        if (tradingChannels.get(i).items[j][0].equals("n")){
                            returnString.append("Nothing here!");
                            break;
                        } else if (tradingChannels.get(i).items[j][1].equalsIgnoreCase("0")) {
                            returnString.append(" ").append(notItems[j]).append(" ");
                        } else {

                            if (!tradingChannels.get(i).items[j][1].equalsIgnoreCase("N/A")) {
                                importantItems[j] = true;
                            }
                            if (importantItems[j]) {
                                returnString.append(" ").append(tradingChannels.get(i).items[j][0]).append(" ");

                                if (!tradingChannels.get(i).items[j][1].equalsIgnoreCase("N/A")) {
                                    returnString.append(tradingChannels.get(i).items[j][1]);
                                }
                            }
                        }
                    }
                }
            }
        }
        return returnString.toString();
    }

    /**
     * checks a specific tradingChannelObject if it has items on it
     * @param tradingChannelObject the specific tradingChannelObject that needs checking
     * @return returns true if channel has item(s)
     */
    private boolean channelHasItems(TradingChannelObject tradingChannelObject) {
        for (int i = 0; i < tradingChannelObject.items.length; i++) {
            if (tradingChannelObject.items[i][0] != null) {
                return true;
            }
        }
        return false;
    }
}
