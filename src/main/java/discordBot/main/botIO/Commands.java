package discordBot.main.botIO;

import discordBot.main.App;
import discordBot.main.fileUtil.Attachments;
import discordBot.main.fileUtil.image.ImageLogic;
import discordBot.main.outputWindow.ChannelHandling.TradingChannelObject;
import discordBot.main.outputWindow.Window.PrintEmbed;
import net.dv8tion.jda.core.entities.*;

import java.io.File;

public class Commands {
    private ImageLogic imageLogic = new ImageLogic();
    private String preFix = ".";
    void serverAdmin(User user, Message objMsg, MessageChannel objChannel) {
        //Splits the command at spaces
        String[] stringInput = objMsg.getContentRaw().split(" ");

        //prints out all the channels available
        if (objMsg.getContentRaw().equalsIgnoreCase(preFix + "ShowAllChannels")) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < discordBot.main.App.textChannels.size(); i++) {
                s.append(i).append(".  ").append(App.textChannels.get(i).toString()).append("\r\n");
            }
            objChannel.sendMessage(s).queue();
        }
        //tests printing in another channel
        if (objMsg.getContentRaw().equalsIgnoreCase(preFix + "printIn")) {
            App.textChannels.get(4).sendMessage("printing in another channel!").queue();

        }

        if (objMsg.getContentRaw().equalsIgnoreCase(preFix + "sendTestImage")) {
            objChannel.sendFile(new File("ImagesDownloaded/test.png")).queue();
        }


    }

    void removeItems(Message objMsg, MessageChannel objChannel, App main) {
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
                        for (int i = 0; i < items.length; i++) {
                            tradingChannel.removeItem(items[i]);
                        }
                        break outerLoop;
                    }
                }

            }
        }
    }
    void printEmbed(MessageChannel objChannel,App main) {
        PrintEmbed printEmbed = new PrintEmbed();
        printEmbed.printEmbed(main,objChannel);
    }
    void addItems(User objUser, Message objMsg, MessageChannel objChannel, App main) {

        Attachments attachments = new Attachments();
        if (!objMsg.getContentRaw().startsWith("!")) {

            String[] inputCmdMsg;

            if (!objMsg.getContentRaw().contains(" ")) {
                inputCmdMsg = new String[2];
                inputCmdMsg[0] = objMsg.getContentRaw();
                inputCmdMsg[1] = "This string is useless but don't remove it";
            } else {
                inputCmdMsg = objMsg.getContentRaw().split(" ");

            }
            String callSign = inputCmdMsg[0];
            String[] temp = objMsg.getContentRaw().substring(4).split(" ");
            for (int i = 0; i < temp.length; i++) {
                temp[i] = temp[i].toLowerCase();
                System.out.println("input is"+temp[i]);
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
                    if (channelCommand.equals("ka") && number < 4 || channelCommand.equals("ar") && number < 1) {
                        break outerLoop;
                    } else if (callSign.startsWith(channelCommand) && callSign.endsWith(String.valueOf(number))) {
                        if (attachments.CheckForAttachments(objMsg)) {
                            if (imageLogic.compareImage(objChannel, objMsg, main)) {
                                printEmbed(objChannel,main);
                            }
                        } else {
                            TradingChannelObject tradingChannel = main.channelManager.getTradingChannelWithCallSignAndId(inputCmdMsg[0].substring(0, 2), Integer.parseInt(String.valueOf(inputCmdMsg[0].charAt(2))), main);
                            for (int i = 0; i < items.length; i++) {
                                try {
                                    tradingChannel.addItem(items[i],amount[i]);

                                } catch (ArrayIndexOutOfBoundsException e) {
                                    tradingChannel.addItem(items[i],null);

                                }
                            }
                            printEmbed(objChannel,main);
                        }
                        break outerLoop;
                    }

                }
            }
        }
    }
    void checkIfChannelsAreNeeded(App main){
        //creates trading channels
        if (main.tradingChannelObjects.isEmpty()) {
            main.channelManager.clearTradingChannels(main);
            main.channelManager.initiateTradingChannels(main);
        }
    }
}

