package discordBot.bot.botIO.input;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;
import java.util.List;

public class GuildHandler {
    /**
     *
     * @param channel the channel that the message that triggered this was sent from
     * @param channelName the name of the channel thats being looked for
     * @return true if channel that message is sent in is equals to channelName
     */
    public boolean checkChannel(MessageChannel channel, String channelName) {
        // Checks if command is written in specific channel.
        return channel.getName().equalsIgnoreCase(channelName);
    }

    /**
     *
     * @param jda the main bots JDA
     * @return returns all the messagechannels on the server
     */
    public MessageChannel[] getMessageChannels(JDA jda) {
        ArrayList<MessageChannel> channels = new ArrayList<>();
        for(Guild g : jda.getGuilds()) {
            for(MessageChannel c : g.getTextChannels()) {
                channels.add(c);
            }
        }
        return channels.toArray(new MessageChannel[0]);
    }

    /**
     *
     * @param roles the roles a specific user has
     * @param s a role to check if user has
     * @return true if user has that role false if else
     */
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
