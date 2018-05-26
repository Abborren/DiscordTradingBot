package discordBot.main.botInput;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class Handler {
    public boolean checkChannel(MessageChannel channel, String channelName) {
        // Checks if command is written in specific channel.
        if (channel.getName().equalsIgnoreCase(channelName)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkRole(List<Role> roles, String s) {
        //compares all the roles of the user to check if it has required role
        for(Role role : roles) {
            if(role.getName().equalsIgnoreCase(s)) {
                return true;

            }
        }
        return false;
    }
}
