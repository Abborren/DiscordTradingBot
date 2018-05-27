package discordBot.bot.botIO.output.tempMessages;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class ResetMessage {
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
