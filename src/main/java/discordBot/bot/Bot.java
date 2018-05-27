

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
    private JDA jdaBot;
    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
    }
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
    public void onMessageReceived(MessageReceivedEvent messageEvent) {
        new MessageReceived(messageEvent,this,jdaBot).messageReceivedHandler();
    }
}