package discordBot.bot.botTime.discordUser;

import net.dv8tion.jda.core.entities.User;

import java.time.LocalDateTime;

public class DiscordUser {
    public LocalDateTime timeUTC;
    public User user;
    public DiscordUser(LocalDateTime timeUTC, User user) {
        this.user = user;
        this.timeUTC = timeUTC.plusHours(16);
    }
}
