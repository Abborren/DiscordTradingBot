package discordBot.main.botIO;

import discordBot.main.App;
import discordBot.main.fileUtil.Attachments;
import discordBot.main.fileUtil.image.ImageLogic;
import discordBot.main.outputWindow.ChannelManager;
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


    void tradingCommands(User objUser, Message objMsg, MessageChannel objChannel, App main) {
        //creates trading channels
        if (main.tradingChannelObjects.isEmpty()) {
            main.channelManager.clearTradingChannels(main);
            main.channelManager.initiateTradingChannels(main);
        }
        Attachments attachments = new Attachments();
        String[] message = new String[2];
        if (objMsg.getContentRaw().contains(" ")) {
            message = objMsg.getContentRaw().split(" ");
        } else {
            message[0] = objMsg.getContentRaw();
            message[1] = "reee";
        }


        for (int i = 0; i < 2; i++) {
            message[i] = message[i].toLowerCase();
        }

        String[] channelCommandsArray = {"ba","ve","se","me","ca","va","ka","ar"};
        int[] channelNumberArray ={1,2,3,4,5,6};
        outerLoop:
        for (String channelCommand : channelCommandsArray) {
            for (int number : channelNumberArray) {
                //if channel limit is met it will break
                if (channelCommand.equals("ka") && number < 4 || channelCommand.equals("ar") && number < 1) {
                    break outerLoop;
                } else if (message[0].startsWith(channelCommand) && message[0].endsWith(String.valueOf(number))) {
                    if (attachments.CheckForAttachments(objMsg)){
                        imageLogic.compareImage(objChannel, objMsg,main);
                    }

                    break outerLoop;
                }

            }
        }

    }

}

