package discordBot.main.botTime;

import discordBot.main.Bot;
import discordBot.main.botIO.input.GuildHandler;
import discordBot.main.botIO.output.ChannelHandling.ChannelManager;
import discordBot.main.botIO.output.Window.PrintEmbed;
import discordBot.main.fileUtil.FileManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.File;
import java.time.*;
import java.util.ArrayList;

public class TimeObj implements Runnable {
    private Bot main;
    private JDA jdaBot;
    private PrintEmbed printEmbed = new PrintEmbed();
    private GuildHandler guildHandler = new GuildHandler();
    public MessageChannel[] messageChannels;

    public TimeObj(Bot main, JDA jdaBot) {
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
            resetTime.add(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.of(reset, 1)));
        }
        int temp = resetTime.size();
        for (int i = 0; i < temp; i++) {
            if (checkTradingReset(LocalDateTime.now(Clock.systemUTC()), resetTime.get(0))) {
                resetTime.add(resetTime.get(0).plusDays(1));
                resetTime.remove(0);
                System.out.println("day added to "+ i); // debug feature
            } else {
                resetTime.add(resetTime.get(0));
                resetTime.remove(0);
            }
        }

        while (true) {
            long currentTimeMillis = System.currentTimeMillis();
            LocalDateTime timeUTC = LocalDateTime.now(Clock.systemUTC());
            if (currentTimeMillis - secondPreviousMillis >= secondInterval) {
                secondPreviousMillis = currentTimeMillis;
                int intTemp = resetTime.size();
                for (int i = 0; i < intTemp; i++) {
                    if (checkTradingReset(timeUTC, resetTime.get(0))) {
                        resetTime.add(resetTime.get(0).plusDays(1));
                        resetTime.remove(0);
                        System.out.println("trading resets");
                        resetTrading();
                        //System.out.println(" current check time "+ resetTime[i]);
                    }
                }
                //System.out.println("current time " + timeUTC.getHour()+":"+timeUTC.getMinute()+":"+timeUTC.getSecond()); // hour:minute:second
            }
            if (currentTimeMillis - minutePreviousMillis >= minuteInterVal) {
                minutePreviousMillis = currentTimeMillis;
                updateGameMessage(timeUTC,resetTime.get(0));
            }
        }
    }
    private boolean checkTradingReset(LocalDateTime timeUTC, LocalDateTime resetTime) {
        return timeUTC.isAfter(resetTime);
    }
    private void updateGameMessage(LocalDateTime timeUTC, LocalDateTime resetTime) {
        //for (int i = 0; i < resetTime.size(); i++) {

            Duration duration = Duration.between(timeUTC, resetTime);
          /*  if(duration.getSeconds() < 0) {
                continue;
            }
            else */{
                long minutes = duration.toMinutes();
                long hrs = 0;
                while (minutes >= 60) {
                    minutes = minutes -60;
                    hrs++;
                }
                if (hrs >= 1) {
                    jdaBot.getPresence().setGame(Game.of(Game.GameType.DEFAULT ,"next reset in "+hrs+"h "+minutes+"mins"));
                } else {
                    jdaBot.getPresence().setGame(Game.of(Game.GameType.DEFAULT ,"next reset in "+minutes+"mins"));
                }

                System.out.println(duration.toMinutes());

            }
        //}
    }
    private void resetTrading() {
        for (int i=0; i< main.tradingChannelObjects.size();i++) {
            for (int j=0; j < main.tradingChannelObjects.get(i).items.length; j++) {
                String item = main.tradingChannelObjects.get(i).items[j][0];
                main.tradingChannelObjects.get(i).removeItem(item);
            }

        }
        for (MessageChannel messageChannel : messageChannels) {
            if (guildHandler.checkChannel(messageChannel,"trade_data_test")) {
                printEmbed.editEmbed(main,messageChannel);
                break;
            }
        }
    }
    private void initiateOutput(JDA jdaBot) {

        ChannelManager channelManager = new ChannelManager();
        channelManager.initiateTradingChannels(main);
        messageChannels = guildHandler.getMessageChannels(jdaBot);
        for (MessageChannel messageChannel : messageChannels) {
            if (guildHandler.checkChannel(messageChannel,"trade_data_test")) {
                clearDiscordChannel(messageChannel);
                FileManager fileManager = new FileManager();
                String s = fileManager.loadString(new File("Config/BotMessage/MessageId.txt"));
                if (s == null) {
                    main.botMessageId = printEmbed.printEmbed(main,messageChannel);
                    fileManager.saveString(new File("Config/BotMessage/MessageId.txt"),main.botMessageId);
                } else {
                    main.botMessageId = s;
                    printEmbed.editEmbed(main,messageChannel);
                }

                //System.out.println("channel id "+main.botMessageId);
                break;
            }
        }
    }

    private void clearDiscordChannel(MessageChannel channel) {
        //channel.getHistory().getMessageById("449918019058270218").delete().queue();

    }
}
