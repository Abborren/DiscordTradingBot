package discordBot.main.botIO.input;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;
import java.util.List;

public class GuildHandler {
    public boolean checkChannel(MessageChannel channel, String channelName) {
        // Checks if command is written in specific channel.
        return channel.getName().equalsIgnoreCase(channelName);
    }
    public MessageChannel[] getMessageChannels(JDA jda) {
        ArrayList<MessageChannel> channels = new ArrayList<>();
        for(Guild g : jda.getGuilds()) {
            for(MessageChannel c : g.getTextChannels()) {
                channels.add(c);
            }
        }
        return channels.toArray(new MessageChannel[0]);
    }
    boolean checkRole(List<Role> roles, String s) {
        //compares all the roles of the user to check if it has required role
        for(Role role : roles) {
            if(role.getName().equalsIgnoreCase(s)) {
                return true;

            }
        }
        return false;
    }
}
