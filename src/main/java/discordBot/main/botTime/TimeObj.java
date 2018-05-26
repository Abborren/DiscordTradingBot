package discordBot.main.botTime;

import discordBot.main.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class TimeObj implements Runnable {
    private Bot main;
    private JDA jdaBot;
    public TimeObj(Bot main, JDA jdaBot) {
        this.main = main;
        this.jdaBot = jdaBot;
    }
    @Override
    public void run() {
        main.initiateOutput(jdaBot);
        long interval = 1000;
        long previousMillis =0;
        LocalDateTime[] resetTime = new LocalDateTime[6];
        int[] resets = {0, 4, 8, 12, 16, 20};
        for (int i = 0; i < resets.length;i++) {
            resetTime[i] = LocalDateTime.of(LocalDate.now(),LocalTime.of(resets[i],0));
        }

        while (true) {
            long currentMillis = System.currentTimeMillis();
            LocalDateTime timeUTC = LocalDateTime.now(Clock.systemUTC());
            if (currentMillis - previousMillis >= interval) {
                previousMillis = currentMillis;
                for (int i = 0; i < resetTime.length; i++) {
                    if (!checkTradingReset(timeUTC, resetTime[i]).toLocalDate().equals(resetTime[i].toLocalDate())) {
                        resetTime[i] = resetTime[i].plusDays(1);
                        //System.out.println(" current check time "+ resetTime[i]);
                    }
                }
                //System.out.println("current time " + timeUTC.getHour()+":"+timeUTC.getMinute()+":"+timeUTC.getSecond()); // hour:minute:second
            }
        }
    }
    private LocalDateTime checkTradingReset(LocalDateTime timeUTC, LocalDateTime resetTime) {

        if (timeUTC.isAfter(resetTime)) {
            System.out.println("trading resets");
            resetTrading();
            return resetTime.plusDays(1);
        }
        return resetTime;
    }
    private void resetTrading() {
        for (int i=0; i< main.tradingChannelObjects.size();i++) {
            for (int j=0; j < main.tradingChannelObjects.get(i).items.length; j++) {
                String item = main.tradingChannelObjects.get(i).items[j][0];
                main.tradingChannelObjects.get(i).removeItem(item);
            }

        }
        /*for (MessageChannel messageChannel : main.messageChannels) {
            if (main.guildHandler.checkChannel(messageChannel,"trade_data_test")) {
                main.printEmbed.printEmbed(main,messageChannel);
                break;
            }
        }*/
    }
}
