package discordBot.main.botTime;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Date;

public class TimeObj implements Runnable {
    @Override
    public void run() {
        Date date;
        long interval = 1000;
        long previousMillis =0;
        while (true) {
            long currentMillis = System.currentTimeMillis();
            LocalTime timeUTC = LocalTime.now(Clock.systemUTC());
            if (currentMillis - previousMillis >= interval) {
                previousMillis = currentMillis;

                System.out.println(timeUTC.getHour()+":"+timeUTC.getMinute()+":"+timeUTC.getSecond()); // 06:08:18.125
            }
        }
    }
}
