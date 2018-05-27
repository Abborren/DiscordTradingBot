package discordBot.bot.botIO.input.commands;

import discordBot.bot.botIO.output.tempMessages.TempMessage;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.List;

public class TradingRoles {
    public void giveRole(MessageChannel channel, Message message, User user, List<Role> userRoles, JDA jdaBot) {
        Thread thread = new Thread(() -> {
            Guild guild = channel.getJDA().getGuilds().get(1);
            GuildController guildController =new GuildController(guild);
            TempMessage tempMessage = new TempMessage();
            String messageS = message.getContentRaw().toLowerCase();
            List<Role> roles = jdaBot.getRolesByName("active",true);

            if (messageS.startsWith("!iam")) {
                if (messageS.endsWith("inactive")) {
                    if (userRoles.contains(roles.get(0))) {
                        guildController.removeRolesFromMember(guild.getMember(user),roles.get(0)).complete();
                        tempMessage.printTempMessage(channel,new MessageBuilder(user.getAsMention()+", you are now inactive!"),6000);
                    } else {
                        tempMessage.printTempMessage(channel,new MessageBuilder(user.getAsMention()+", you are already inactive"),6000);
                    }

                } else if (messageS.endsWith("active")) {
                    if (!userRoles.contains(roles.get(0))) {
                        guildController.addRolesToMember(guild.getMember(user),roles.get(0)).complete();
                        tempMessage.printTempMessage(channel,new MessageBuilder(user.getAsMention()+", you are now active!"),6000);

                    } else {
                        tempMessage.printTempMessage(channel,new MessageBuilder(user.getAsMention()+", you are already active!"),6000);
                    }
                }
            }
        });
        thread.start();

    }
}
