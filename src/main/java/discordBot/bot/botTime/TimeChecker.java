package discordBot.bot.botTime;

import discordBot.bot.Bot;

import java.time.Clock;
import java.time.LocalDateTime;

public class TimeChecker implements Runnable {
    Bot main;
    public TimeChecker(Bot bot) {
        this.main = bot;
    }

    @Override
    public void run() {
        long minuteInterVal = 5*59999;
        long minutePreviousMillis = 1;
        while (main.running) {
            LocalDateTime timeBefore = main.botTiming.timeUTC.plusMinutes(5);

            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - minutePreviousMillis >= minuteInterVal) {
                minutePreviousMillis = currentTimeMillis;
                if (timeBefore.isBefore(LocalDateTime.now(Clock.systemUTC()))) {
                    System.out.println("SOMETHING IS WRONG\n Restarting time counting!");
                    main.startTiming(true);
                }
            }
        }
    }
}
