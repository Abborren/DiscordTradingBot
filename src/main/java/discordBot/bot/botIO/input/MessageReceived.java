package discordBot.bot.botIO.input;
import discordBot.bot.Bot;
import discordBot.bot.botIO.input.commands.AddItems;
import discordBot.bot.botIO.input.commands.RemoveItems;
import discordBot.bot.botIO.input.commands.TradingRoles;
import discordBot.bot.botIO.input.commands.WipeChannel;
import discordBot.bot.botIO.output.tempMessages.TempMessage;
import discordBot.bot.fileUtil.FileManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.List;

public class MessageReceived {

    private GuildHandler guildHandler = new GuildHandler();
    //bot
    private Bot main;
    //Obtains properties of the received message
    private Message thisMsg;
    private MessageChannel thisChannel;
    private User thisUser;
    // gets a list of all the roles that user has
    private List<Role> roles;
    // gets Jda bot
    private JDA jdaBot;

    /**
     * this handles all messages that are recived from discord
     * @param messageEvent something message related happened on discord
     * @param main the Bots main
     * @param jdaBot the discord api
     */
    public MessageReceived(MessageReceivedEvent messageEvent, Bot main,JDA jdaBot) {
        thisMsg = messageEvent.getMessage();
        thisChannel = messageEvent.getChannel();
        thisUser = messageEvent.getAuthor();
        roles = messageEvent.getGuild().getMember(thisUser).getRoles();
        this.main = main;
        this.jdaBot = jdaBot;
    }

    /**
     * where you add commands and new functions that are to be triggered on specific things
     */
    public void messageReceivedHandler() {
    //makes bot unable to respond to its own message

        if(!thisUser.isBot()) {
        //Admin only input.
        if (guildHandler.checkRole(roles, "Moderator") || guildHandler.checkRole(roles, "Owner") ) {
            WipeChannel wipeChannel = new WipeChannel();
            wipeChannel.wipeChannel(thisChannel,thisMsg,thisUser,main);
        }
        if (guildHandler.checkRole(roles, "Moderator") || guildHandler.checkRole(roles, "Owner") || guildHandler.checkRole(roles, "Helper")) {
            new RemoveItems().resetManualAllItems(thisMsg,thisChannel,main);
        }

        //Example of Role specific role
        if (guildHandler.checkChannel(thisChannel, new FileManager().loadString(new File("Config/Variables/Channels/RolesChannel.txt")))) {
            DeleteInputMessage deleteInputMessage = new DeleteInputMessage();
            TradingRoles tradingRoles = new TradingRoles();
            tradingRoles.giveRole(thisChannel,thisMsg,thisUser,roles, jdaBot,main);
            deleteInputMessage.deleteMessage(thisChannel);
        }
            //for input addItems in a specific channel in this case "input-channel"
            if (guildHandler.checkChannel(thisChannel, new FileManager().loadString(new File("Config/Variables/Channels/InputOnlyChannel.txt")))) {
                DeleteInputMessage deleteInputMessage = new DeleteInputMessage();
                AddItems addItems = new AddItems();
                RemoveItems removeItems = new RemoveItems();
                for (MessageChannel messageChannel : guildHandler.getMessageChannels(jdaBot)) {
                    if (guildHandler.checkChannel(messageChannel, new FileManager().loadString(new File("Config/Variables/Channels/TradingChannel.txt")))) {
                        addItems.addItems(thisMsg,messageChannel,main);
                        removeItems.removeItems(thisMsg,messageChannel,main);
                        deleteInputMessage.deleteMessage(thisChannel);
                        new TempMessage().printTempMessage(thisChannel,new MessageBuilder(thisUser.getAsMention()+", Message input received!"),10000);
                        break;
                    }
                }

            }
        //for input addItems in a specific channel in this case "input-channel"
        if (guildHandler.checkChannel(thisChannel, new FileManager().loadString(new File("Config/Variables/Channels/TradingChannel.txt")))) {
            DeleteInputMessage deleteInputMessage = new DeleteInputMessage();
            new AddItems().addItems(thisMsg, thisChannel,main);
            new RemoveItems().removeItems(thisMsg,thisChannel,main);
            deleteInputMessage.deleteMessage(thisChannel);
        }


    }
}
}
