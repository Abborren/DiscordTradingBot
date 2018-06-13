

package discordBot.bot;
import javax.security.auth.login.LoginException;

import discordBot.bot.botIO.input.MessageReceived;
import discordBot.bot.botIO.output.ChannelHandling.TradingChannelObject;
import discordBot.bot.botTime.BotTiming;
import discordBot.bot.botTime.discordUser.DiscordUser;
import discordBot.tokenUtil.TokenUtil;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

public class Bot extends ListenerAdapter {
    public ArrayList<TradingChannelObject> tradingChannelObjects = new ArrayList<TradingChannelObject>();
    public ArrayList<DiscordUser> discordUsers = new ArrayList<>();
    public String botMessageId;
    public String gameMessage = "next reset in ?min";
    private JDA jdaBot;
    public boolean running = true;

    /**
     * this starts the bot
     * @param args arguments, not used for anything
     * @throws LoginException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
    }

    /**
     *
     * @throws LoginException if bot cannot log in
     * @throws InterruptedException if bot looses internet connection
     */
    private Bot() throws LoginException, InterruptedException {
        //loads token loading class
        TokenUtil tokenUtil = new TokenUtil();
        //Initializes the bot
        jdaBot = new JDABuilder(AccountType.BOT).setToken(tokenUtil.loadToken()).buildBlocking();
        jdaBot.addEventListener(this);
        BotTiming botTiming = new BotTiming(this,jdaBot);
        Thread timeThread = new Thread(botTiming);
        timeThread.start();
    }
    @Override
    /**
     * this is where the message recived event is initially handled
     */
    public void onMessageReceived(MessageReceivedEvent messageEvent) {
        new MessageReceived(messageEvent,this,jdaBot).messageReceivedHandler();
    }
}