package discordBot.bot.botIO.input.commands;

import discordBot.bot.Bot;
import discordBot.bot.botIO.output.tempMessages.TempMessage;
import discordBot.bot.botTime.discordUser.DiscordUser;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class TradingRoles {
    /**
     *
     * @param channel the specific channel where the message that triggered this funcion was sent
     * @param message a discord message
     * @param user the specific user
     * @param userRoles the roles a specific user has currently
     * @param jdaBot the discord Bot
     * @param main the Bot main
     */
    public void giveRole(MessageChannel channel, Message message, User user, List<Role> userRoles, JDA jdaBot,Bot main) {
        Thread thread = new Thread(() -> {
            Guild guild = channel.getJDA().getGuilds().get(1);
            GuildController guildController = new GuildController(guild);
            TempMessage tempMessage = new TempMessage();
            String messageS = message.getContentRaw().toLowerCase();
            List<Role> roles = jdaBot.getRolesByName("active",true);

            if (messageS.startsWith("!iam")) {
                if (messageS.endsWith("inactive")) {
                    if (userRoles.contains(roles.get(0))) {
                        guildController.removeRolesFromMember(guild.getMember(user),roles.get(0)).complete();
                        tempMessage.printTempMessage(channel,new MessageBuilder(user.getAsMention()+", you are now inactive!"),6000);

                        for (int i = 0; i < main.discordUsers.size(); i++) {
                            if (main.discordUsers.get(i).user.getId().equalsIgnoreCase(user.getId())) {
                                main.discordUsers.remove(main.discordUsers.get(i));
                                break;
                            }
                        }
                    } else {
                        tempMessage.printTempMessage(channel,new MessageBuilder(user.getAsMention()+", you are already inactive"),6000);
                    }

                } else if (messageS.endsWith("active")) {
                    if (!userRoles.contains(roles.get(0))) {
                        guildController.addRolesToMember(guild.getMember(user),roles.get(0)).complete();
                        main.discordUsers.add(new DiscordUser(LocalDateTime.now(Clock.systemUTC()),user));
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
