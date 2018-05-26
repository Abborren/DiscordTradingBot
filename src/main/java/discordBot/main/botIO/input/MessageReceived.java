package discordBot.main.botIO.input;
import discordBot.main.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class MessageReceived {
    private Commands commands = new Commands();
    private Handler handler = new Handler();
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
        commands.checkIfChannelsAreNeeded(main);
        if(!thisUser.isBot())

    {
        //Admin only input.
        if (handler.checkRole(roles, "admin")) {
            commands.serverAdmin(thisUser, thisMsg, thisChannel);
        }

        //Example of Role specific role
        if (handler.checkRole(roles, "ExampleRole")) {
            //addItems(thisUser, thisMsg, thisChannel);
        }
        //for input addItems in a specific channel in this case "input-channel"
        if (handler.checkChannel(thisChannel, "trade_data_test")) {
            commands.addItems(thisUser, thisMsg, thisChannel,main);
            commands.removeItems(thisMsg,thisChannel,main);
        }


    }
}
}
