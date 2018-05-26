package discordBot.main.botIO.input.commands;

import discordBot.main.Bot;
import discordBot.main.botIO.input.TradingInput;
import discordBot.main.botIO.output.ChannelHandling.TradingChannelObject;
import discordBot.main.fileUtil.Attachments;
import discordBot.main.fileUtil.image.ImageLogic;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class AddItems {
    private ImageLogic imageLogic = new ImageLogic();
    public void addItems(Message objMsg, MessageChannel objChannel, Bot main) {

        Attachments attachments = new Attachments();
        if (!objMsg.getContentRaw().startsWith("!")) {

            String[] inputMsg;
            String[] temp;
            if (!objMsg.getContentRaw().contains(" ")) {
                inputMsg = new String[2];
                temp = new String[2];
                temp[0] = "N/A";
                temp[1] = "N/A";
                inputMsg[0] = objMsg.getContentRaw();
                inputMsg[1] = "This string is useless but don't remove it";
            } else {
                inputMsg = objMsg.getContentRaw().split(" ");
                temp = objMsg.getContentRaw().substring(4).split(" ");
            }
            for (int i = 0; i < inputMsg.length; i++) {
                inputMsg[i] = inputMsg[i].toLowerCase();
            }

            TradingInput tradingInput = new TradingInput();
            String[] items = tradingInput.returnItems(temp);
            String[] amount = tradingInput.returnAmount(temp);
            String[] channelCommandsArray = {"ba", "ve", "se", "me", "ca", "va", "ka", "ar"};
            int[] channelNumberArray = {1, 2, 3, 4, 5, 6};

            outerLoop:
            for (String channelCommand : channelCommandsArray) {
                for (int number : channelNumberArray) {
                    //if channel limit is met it will break
                    if (inputMsg[0].substring(0, 1).equals("ar") && number >= 2 || inputMsg[0].substring(0, 1).equals("ka") && number >= 5) {
                        break outerLoop;
                    }
                    else if (inputMsg[0].startsWith(channelCommand) && inputMsg[0].endsWith(String.valueOf(number))) {
                        if (attachments.CheckForAttachments(objMsg)) {
                            if (imageLogic.compareImage(objChannel, objMsg, main)) {
                                main.printEmbed.editEmbed(main,objChannel);
                            }
                        } else {
                            TradingChannelObject tradingChannel = main.channelManager.getTradingChannelWithCallSignAndId(inputMsg[0].substring(0, 2), Integer.parseInt(String.valueOf(inputMsg[0].charAt(2))), main);
                            for (int i = 0; i < items.length; i++) {
                                try {
                                    tradingChannel.addItem(items[i],amount[i]);

                                } catch (ArrayIndexOutOfBoundsException e) {
                                    tradingChannel.addItem(items[i],null);

                                }
                            }
                            main.printEmbed.editEmbed(main,objChannel);
                        }
                        break outerLoop;
                    }

                }
            }
        }
    }
}
