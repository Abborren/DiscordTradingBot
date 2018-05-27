package discordBot.bot.botIO.input.commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.List;

public class TradingRoles {
    public void giveRole(MessageChannel channel, Message message, User user, List<Role> userRoles, JDA jdaBot) {
        Thread thread = new Thread(() -> {
            Guild guild = channel.getJDA().getGuilds().get(1);
            GuildController guildController =new GuildController(guild);
            String messageS = message.getContentRaw().toLowerCase();
            List<Role> roles = jdaBot.getRolesByName("active",true);

            if (messageS.startsWith("!iam")) {
                if (messageS.endsWith("inactive")) {
                    if (userRoles.contains(roles.get(0))) {
                        guildController.removeRolesFromMember(guild.getMember(user),roles.get(0)).complete();
                        printTempMessage(channel,", you are now inactive!",user);
                    } else {
                        System.out.println("user is already inactive");
                        printTempMessage(channel,", you are already inactive",user);
                    }

                } else if (messageS.endsWith("active")) {
                    if (!userRoles.contains(roles.get(0))) {
                        guildController.addRolesToMember(guild.getMember(user),roles.get(0)).complete();
                        printTempMessage(channel,", you are now active!",user);

                    } else {
                        printTempMessage(channel,", you are already active!",user);
                    }
                }
            }
        });
        thread.start();

    }
    private void printTempMessage (MessageChannel channel,String s,User user) {
        String id = channel.sendMessage(user.getAsMention()+s).complete().getId();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.deleteMessageById(id).complete();
    }

}
