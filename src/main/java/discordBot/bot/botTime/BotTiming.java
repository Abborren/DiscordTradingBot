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
    private boolean reset;
    private PrintEmbed printEmbed = new PrintEmbed();
    private GuildHandler guildHandler = new GuildHandler();
    private MessageChannel[] messageChannels;

    /**
     * creates this timing object
     * @param main the bots main
     * @param jdaBot the bots JDA
     */
    public BotTiming(Bot main, JDA jdaBot, boolean reset) {
        this.main = main;
        this.jdaBot = jdaBot;
        this.reset = reset;
    }
    @Override
    public void run() {
        initiateOutput(jdaBot, reset);
        long secondInterval = 1000;
        long minuteInterVal = 60000;
        long secondPreviousMillis = 0;
        long minutePreviousMillis = 0;
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
                        main.gameMessage = updateGameMessage(timeUTC,resetTime.get(0));
                        editEmbed();
                    }
                }
                //System.out.println("current time " + timeUTC.getHour()+":"+timeUTC.getMinute()+":"+timeUTC.getSecond()); // hour:minute:second
            }
            if (currentTimeMillis - minutePreviousMillis >= minuteInterVal) {
                minutePreviousMillis = currentTimeMillis;
                userRoleRemoval(timeUTC);
                main.gameMessage = updateGameMessage(timeUTC,resetTime.get(0));
                editEmbed();

            }
        }
    }

    /**
     * this checks if 16 hrs have passed since user in DiscordUser object requested "active" role
     * @param timeUTC the current UTC time
     */
    private void userRoleRemoval(LocalDateTime timeUTC) {
        for (int i = 0; i < main.discordUsers.size();i++) {
            if (checkIfTimeXIsAfterTimeY(timeUTC,main.discordUsers.get(i).timeUTC)) {
                //System.out.println(main.discordUsers.get(i).timeUTC); // debug feature
                Guild guild = jdaBot.getGuilds().get(0);
                List<Role> roles = jdaBot.getRolesByName("active",true);
                GuildController guildController = new GuildController(guild);
                guildController.removeRolesFromMember(guild.getMember(main.discordUsers.get(i).user),roles.get(0)).complete();
                System.out.println("User "+ main.discordUsers.get(i).user.getName()+" has lost active role! at "+timeUTC.getHour()+":"+timeUTC.getMinute()+"!");
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
     * this updates the game message to the time until next reset
     * @param timeUTC the current time in UTC
     * @param resetTime the next reset in UTC
     */
    private String updateGameMessage(LocalDateTime timeUTC, LocalDateTime resetTime) {

        Duration duration = Duration.between(timeUTC, resetTime);
        String returnString;
        long minutes = duration.toMinutes();
        long hrs = 0;
        while (minutes >= 60) {
            minutes -= 60;
            hrs++;
        }
        if (hrs >= 1) {
            returnString = "next reset in "+hrs+"h "+minutes+"min";

        } else {
            returnString = "next reset in "+minutes+"min";
        }
        jdaBot.getPresence().setGame(Game.of(Game.GameType.DEFAULT ,returnString));
        return returnString;
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
    private void initiateOutput(JDA jdaBot,boolean reset) {

        if (!reset) {
            ChannelManager channelManager = new ChannelManager();
            channelManager.initiateTradingChannels(main);
        }
        messageChannels = guildHandler.getMessageChannels(jdaBot);
        editEmbed();

    }
    private void editEmbed() {
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
