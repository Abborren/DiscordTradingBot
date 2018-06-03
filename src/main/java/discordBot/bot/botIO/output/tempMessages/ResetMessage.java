package discordBot.bot.botIO.output.tempMessages;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class ResetMessage {
    /**
     * sends out a temporary reset message with a specific string and mentions an specifc role
     * @param channel the specific message channel where the message to be sent in
     * @param roles givs a list of the roles on the server, will be used to ping a specific role
     */
    public void resetMessage(MessageChannel channel, List<Role> roles) {

        Thread thread = new Thread(() -> {
            String messageId = sendMessage(channel,roles);
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (messageId != null) {
                channel.deleteMessageById(messageId).complete();
            }
        });
        thread.start();
    }

    /**
     * This sends out the the reset message
     * @param channel this is the specifed messageChannel for the message to be sent in.
     * @param roles the list of roles
     * @returns the channel id  so that after a certain time the message can be deleted
     */
    private String sendMessage(MessageChannel channel, List<Role> roles) {
        for (Role role : roles) {
            if (role.getName().equalsIgnoreCase("Active")) {
                return channel.sendMessage(role.getAsMention()+" Imperial Trading Reset \n" +
                        "Wait a minute or two for channels to reset before reporting.\n" +
                        "Do not forget to update the itemcount after you have sold your items.\n" +
                        "Thank you for using Abborren's BDO Tools").complete().getId();
            }
        }
        return null;
    }
}
