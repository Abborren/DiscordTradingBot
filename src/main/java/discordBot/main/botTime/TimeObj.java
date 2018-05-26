package discordBot.main.botTime;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class TimeObj implements Runnable {
    @Override
    public void run() {

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
                    if (!checkTradingReset(timeUTC, resetTime[i]).equals(resetTime[i])) {
                        resetTime[i] = resetTime[i].plusDays(1);
                    }
                }
                //System.out.println(timeUTC.getHour()+":"+timeUTC.getMinute()+":"+timeUTC.getSecond()); // hour:minute:second
            }
        }
    }
    private LocalDateTime checkTradingReset(LocalDateTime timeUTC, LocalDateTime resetTime) {

        if (timeUTC.isAfter(resetTime)) {
            System.out.println("trading resets");

            return resetTime.plusDays(1);
        }
        return resetTime;
    }
}
