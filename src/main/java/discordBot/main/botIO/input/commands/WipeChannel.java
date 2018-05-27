package discordBot.main.botIO.input.commands;

import discordBot.main.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

import java.util.List;

public class WipeChannel {
    public void wipeChannel(MessageChannel channel, Message message, User user, Bot main) {
        Thread thread = new Thread(() -> {
            try {
                String input = message.getContentRaw().toLowerCase();
                String sTemp = input.substring(15);
                int amount = Integer.parseInt(sTemp);
                input = input.substring(0,14);
                if (input.equalsIgnoreCase("!clear channel")) {
                    //for (int i=0; i < channel.getMessageById().)
                    List<Message> messages = channel.getHistory().retrievePast(amount).complete();
                    for (Message thisMessage : messages) {
                        if (!thisMessage.getId().equalsIgnoreCase(main.botMessageId)) {
                            thisMessage.delete().complete();
                        }

                    }
                    String id = channel.sendMessage(user.getAsMention()+" Message clearing is complete").complete().getId();
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.deleteMessageById(id).complete();
                }
            } catch (ErrorResponseException e) {
                System.out.println(e);
            } catch (NumberFormatException f) {
                String id = channel.sendMessage(user.getAsMention()+" Message clearing failed, message deletion amount is not a number or something went wrong.").complete().getId();
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException j) {
                    j.printStackTrace();
                }
                channel.deleteMessageById(id).complete();
            }
        });
        thread.start();
    }
}
