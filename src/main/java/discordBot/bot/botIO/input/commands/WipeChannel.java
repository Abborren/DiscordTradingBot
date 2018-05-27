package discordBot.bot.botIO.input.commands;

import discordBot.bot.Bot;
import discordBot.bot.botIO.output.tempMessages.TempMessage;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

import java.util.List;

public class WipeChannel {
    public void wipeChannel(MessageChannel channel, Message message, User user, Bot main) {
        Thread thread = new Thread(() -> {
            try {
                if (message.getContentRaw().startsWith("!clear")) {
                    String input = message.getContentRaw().toLowerCase();
                    String sTemp = input.substring(15);
                    int amount = Integer.parseInt(sTemp);
                    input = input.substring(0, 14);
                    if (input.equalsIgnoreCase("!clear channel")) {
                        //for (int i=0; i < channel.getMessageById().)
                        List<Message> messages = channel.getHistory().retrievePast(amount).complete();
                        for (Message thisMessage : messages) {
                            if (!thisMessage.getId().equalsIgnoreCase(main.botMessageId)) {
                                thisMessage.delete().complete();
                            }

                        }
                        new TempMessage().printTempMessage(channel, new MessageBuilder(user.getAsMention() + " Message clearing is complete"), 20000);
                    }
                }
            } catch(ErrorResponseException e){
                System.out.println(e);
            } catch(NumberFormatException f){
                new TempMessage().printTempMessage(channel, new MessageBuilder(user.getAsMention() + " Message clearing failed, message deletion amount is not a number or something went wrong."), 20000);
            }
        });
        thread.start();
    }
}
