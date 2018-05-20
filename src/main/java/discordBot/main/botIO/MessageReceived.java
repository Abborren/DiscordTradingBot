package discordBot.main.botIO;
import discordBot.main.App;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class MessageReceived {
    Commands commands = new Commands();
    Handler handler = new Handler();
    //main
    private App main;
    //Obtains properties of the received message
    private Message thisMsg;
    private MessageChannel thisChannel;
    private User thisUser;
    // gets a list of all the roles that user has
    private List<Role> roles;

    public MessageReceived(MessageReceivedEvent messageEvent, App main) {
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
        //Admin only botIO.
        if (handler.checkRole(roles, "admin")) {
            commands.serverAdmin(thisUser, thisMsg, thisChannel);
        }
        if (!handler.checkRole(roles,"admin")) {
            commands.nonAdmin(thisUser,thisMsg,thisChannel);
        }
        //Example of Role specific serverWide
        if (handler.checkRole(roles, "ExampleRole")) {
            //serverWide(thisUser, thisMsg, thisChannel);
        }
        //for input serverWide in a specific channel in this case "input-channel"
        if (handler.checkChannel(thisChannel, "input-channel")) {
            commands.serverWide(thisUser, thisMsg, thisChannel);
        }
        else {
            commands.serverWide(thisUser,thisMsg,thisChannel);
        }

    }
}
}
