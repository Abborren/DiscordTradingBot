package discordBot.main.botIO.input;
import discordBot.main.Bot;
import discordBot.main.botIO.input.commands.AddItems;
import discordBot.main.botIO.input.commands.RemoveItems;
import discordBot.main.botIO.input.commands.WipeChannel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class MessageReceived {

    private GuildHandler guildHandler = new GuildHandler();
    //main
    private Bot main;
    //Obtains properties of the received message
    private Message thisMsg;
    private MessageChannel thisChannel;
    private User thisUser;
    // gets a list of all the roles that user has
    private List<Role> roles;

    public MessageReceived(MessageReceivedEvent messageEvent, Bot main) {
        thisMsg = messageEvent.getMessage();
        thisChannel = messageEvent.getChannel();
        thisUser = messageEvent.getAuthor();
        roles = messageEvent.getGuild().getMember(thisUser).getRoles();
        this.main = main;
    }

    public void messageReceivedHandler() {
    //makes bot unable to respond to its own message

        if(!thisUser.isBot())

    {
        //Admin only input.
        if (guildHandler.checkRole(roles, "Moderator") || guildHandler.checkRole(roles, "Owner") ) {
            WipeChannel wipeChannel = new WipeChannel();
            wipeChannel.wipeChannel(thisChannel,thisMsg,thisUser,main);
        }

        //Example of Role specific role
        if (guildHandler.checkRole(roles, "ExampleRole")) {
            //addItems(thisUser, thisMsg, thisChannel);
        }
        //for input addItems in a specific channel in this case "input-channel"
        if (guildHandler.checkChannel(thisChannel, "trade_data_test")) {
            DeleteInputMessage deleteInputMessage = new DeleteInputMessage();
            AddItems addItems = new AddItems();
            RemoveItems removeItems = new RemoveItems();

            addItems.addItems(thisMsg, thisChannel,main);
            removeItems.removeItems(thisMsg,thisChannel,main);
            deleteInputMessage.deleteMessage(thisChannel);
        }


    }
}
}
