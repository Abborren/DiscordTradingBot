package discordBot.bot.botTime.discordUser;

import net.dv8tion.jda.core.entities.User;

import java.time.LocalDateTime;

/**
 * this is where users that requested "active" roles are ut which adds a 16 hr cooldown.
 */
public class DiscordUser {
    public LocalDateTime timeUTC;
    public User user;
    public DiscordUser(LocalDateTime timeUTC, User user) {
        this.user = user;
        this.timeUTC = timeUTC.plusHours(16);
    }
}
