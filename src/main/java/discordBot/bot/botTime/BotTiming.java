package discordBot.bot.botTime;

import discordBot.bot.Bot;
import discordBot.bot.botIO.input.GuildHandler;
import discordBot.bot.botIO.output.ChannelHandling.ChannelManager;
import discordBot.bot.botIO.output.Window.PrintEmbed;
import discordBot.bot.botIO.output.tempMessages.ResetMessage;
import discordBot.bot.fileUtil.FileManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.managers.GuildController;

import java.io.File;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class BotTiming implements Runnable {
    private Bot main;
    private JDA jdaBot;
    private PrintEmbed printEmbed = new PrintEmbed();
    private GuildHandler guildHandler = new GuildHandler();
    private MessageChannel[] messageChannels;

    /**
     * creates this timing object
     * @param main the bots main
     * @param jdaBot the bots JDA
     */
    public BotTiming(Bot main, JDA jdaBot) {
        this.main = main;
        this.jdaBot = jdaBot;
    }
    @Override
    public void run() {
        initiateOutput(jdaBot);
        long secondInterval = 1000;
        long minuteInterVal = 60000;
        long secondPreviousMillis =0;
        long minutePreviousMillis =0;
        ArrayList<LocalDateTime> resetTime = new ArrayList<>();
        int[] resets = {0, 4, 8, 12, 16, 20};
        for (int reset : resets) {
            resetTime.add(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.of(reset, 0)));
        }
        int temp = resetTime.size();
        for (int i = 0; i < temp; i++) {
            if (checkIfTimeXIsAfterTimeY(LocalDateTime.now(Clock.systemUTC()), resetTime.get(0))) {
                resetTime.add(resetTime.get(0).plusDays(1));
                resetTime.remove(0);
                //System.out.println("day added to "+ i); // debug feature
            } else {
                break;
            }
        }

        while (main.running) {
            long currentTimeMillis = System.currentTimeMillis();
            LocalDateTime timeUTC = LocalDateTime.now(Clock.systemUTC());
            if (currentTimeMillis - secondPreviousMillis >= secondInterval) {
                secondPreviousMillis = currentTimeMillis;
                int intTemp = resetTime.size();
                for (int i = 0; i < intTemp; i++) {
                    if (checkIfTimeXIsAfterTimeY(timeUTC, resetTime.get(0))) {
                        System.out.println("trading resets and time is "+resetTime.get(0));
                        resetTime.add(resetTime.get(0).plusDays(1));
                        resetTime.remove(0);
                        resetTrading();
                        updateGameMessage(timeUTC,resetTime.get(0));
                    }
                }
                //System.out.println("current time " + timeUTC.getHour()+":"+timeUTC.getMinute()+":"+timeUTC.getSecond()); // hour:minute:second
            }
            if (currentTimeMillis - minutePreviousMillis >= minuteInterVal) {
                minutePreviousMillis = currentTimeMillis;
                userRoleRemoval(timeUTC);
                updateGameMessage(timeUTC,resetTime.get(0));
            }
        }
    }

    /**
     * this checks if 16 hrs have passed since user in DiscordUser object requested "active" role
     * @param timeUTC the current UTC time
     */
    private void userRoleRemoval(LocalDateTime timeUTC) {
        for (int i = 0; i < main.discordUsers.size();i++) {
            if (!checkIfTimeXIsAfterTimeY(timeUTC,main.discordUsers.get(i).timeUTC)) {
                Guild guild = jdaBot.getGuilds().get(1);
                List<Role> roles = jdaBot.getRolesByName("active",true);
                GuildController guildController = new GuildController(guild);
                guildController.removeRolesFromMember(guild.getMember(main.discordUsers.get(i).user),roles.get(0)).complete();
                main.discordUsers.remove(main.discordUsers.get(i));
            }
        }
    }

    /**
     * this check if one time is after another time
     * @param timeUTC the current time in UTC
     * @param resetTime the reset time in UTC
     * @return if time x is after time y returns true else returns false
     */
    private boolean checkIfTimeXIsAfterTimeY(LocalDateTime timeUTC, LocalDateTime resetTime) {
        return timeUTC.isAfter(resetTime);
    }

    /**
     * this updates the game message to the time untill next reset
     * @param timeUTC the current time in UTC
     * @param resetTime the next reset in UTC
     */
    private void updateGameMessage(LocalDateTime timeUTC, LocalDateTime resetTime) {

        Duration duration = Duration.between(timeUTC, resetTime);

        long minutes = duration.toMinutes();
        long hrs = 0;
        while (minutes >= 60) {
            minutes -= 60;
            hrs++;
        }
        if (hrs >= 1) {
            jdaBot.getPresence().setGame(Game.of(Game.GameType.DEFAULT ,"next reset in "+hrs+"h "+minutes+"min"));
        } else {
            jdaBot.getPresence().setGame(Game.of(Game.GameType.DEFAULT ,"next reset in "+minutes+"min"));
        }
    }

    /**
     * this resets trading when a reset has just triggered
     */
    private void resetTrading() {
        for (int i=0; i< main.tradingChannelObjects.size();i++) {
            for (int j=0; j < main.tradingChannelObjects.get(i).items.length; j++) {
                String item = main.tradingChannelObjects.get(i).items[j][0];
                main.tradingChannelObjects.get(i).removeItem(item);
            }

        }
        for (MessageChannel messageChannel : messageChannels) {
            if (guildHandler.checkChannel(messageChannel,new FileManager().loadString(new File("Config/Variables/Channels/TradingChannel.txt")))) {
                printEmbed.editEmbed(main,messageChannel);
                ResetMessage resetMessage = new ResetMessage();
                resetMessage.resetMessage(messageChannel,jdaBot.getRoles());
                break;
            }
        }
    }

    /**
     * this initiates the trading channels and creates a new embed/edits it
     * @param jdaBot the Discord bots JDA
     */
    private void initiateOutput(JDA jdaBot) {

        ChannelManager channelManager = new ChannelManager();
        channelManager.initiateTradingChannels(main);
        messageChannels = guildHandler.getMessageChannels(jdaBot);
        for (MessageChannel messageChannel : messageChannels) {
            if (guildHandler.checkChannel(messageChannel,new FileManager().loadString(new File("Config/Variables/Channels/TradingChannel.txt")))) {
                FileManager fileManager = new FileManager();
                String s = fileManager.loadString(new File("Config/BotMessage/MessageId.txt"));
                if (s == null || s.equals("")) {
                    main.botMessageId = printEmbed.printEmbed(main,messageChannel);
                    fileManager.saveString(new File("Config/BotMessage/MessageId.txt"),main.botMessageId);
                } else {
                    main.botMessageId = s;
                    printEmbed.editEmbed(main,messageChannel);
                }

                //System.out.println("channel id "+bot.botMessageId);
                break;
            }
        }
    }
}
