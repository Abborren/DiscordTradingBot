package discordBot.main.botIO.input;

import discordBot.main.Bot;
import net.dv8tion.jda.core.entities.*;

import java.io.File;

class Commands {

    private String preFix = ".";
    void serverAdmin(User user, Message objMsg, MessageChannel objChannel) {
        //prints out all the channels available
        if (objMsg.getContentRaw().equalsIgnoreCase(preFix + "ShowAllChannels")) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < Bot.textChannels.size(); i++) {
                s.append(i).append(".  ").append(Bot.textChannels.get(i).toString()).append("\r\n");
            }
            objChannel.sendMessage(s).queue();
        }
        //tests printing in another channel
        if (objMsg.getContentRaw().equalsIgnoreCase(preFix + "printIn")) {
            Bot.textChannels.get(4).sendMessage("printing in another channel!").queue();

        }

        if (objMsg.getContentRaw().equalsIgnoreCase(preFix + "sendTestImage")) {
            objChannel.sendFile(new File("ImagesDownloaded/test.png")).queue();
        }


    }




}

