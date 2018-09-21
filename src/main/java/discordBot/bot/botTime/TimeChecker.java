package discordBot.bot.botTime;

import discordBot.bot.Bot;

import java.time.Clock;
import java.time.LocalDateTime;

public class TimeChecker implements Runnable {
    private Bot main;
    public TimeChecker(Bot bot) {
        this.main = bot;
    }

    @Override
    public void run() {
        long minuteInterVal = 5*59999;
        long minutePreviousMillis = 1;
        LocalDateTime timeBefore = LocalDateTime.now(Clock.systemUTC()).plusMinutes(5);
        while (main.running) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - minutePreviousMillis >= minuteInterVal) {

                if (main.botTiming.timeUTC != null) {
                    timeBefore = main.botTiming.timeUTC.plusMinutes(5);
                    //System.out.println("Time right now is "+LocalDateTime.now(Clock.systemUTC())+" the time before + 1min is "+timeBefore.toString()+""); // debug
                }
                minutePreviousMillis = currentTimeMillis;
                if (timeBefore.isBefore(LocalDateTime.now(Clock.systemUTC()))) {
                    System.out.println("SOMETHING IS WRONG\n Restarting time counting! and the time is "+ LocalDateTime.now(Clock.systemUTC()).toString());
                    main.startTiming(true);
                }
            }
        }
    }
}
