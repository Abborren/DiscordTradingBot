

package discordBot.main;
import javax.security.auth.login.LoginException;

import discordBot.main.botIO.input.GuildHandler;
import discordBot.main.botIO.input.MessageReceived;
import discordBot.main.botIO.output.ChannelHandling.ChannelManager;
import discordBot.main.botIO.output.ChannelHandling.TradingChannelObject;
import discordBot.main.botIO.output.Window.PrintEmbed;
import discordBot.main.botTime.TimeObj;
import discordBot.tokenUtil.TokenUtil;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

public class Bot extends ListenerAdapter {
    public static ArrayList<TextChannel> textChannels = new ArrayList<TextChannel>();
    public  ArrayList<TradingChannelObject> tradingChannelObjects = new ArrayList<TradingChannelObject>();
    public String botMessageId;

    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
    }
    private Bot() throws LoginException, InterruptedException {
        //loads token loading class
        TokenUtil tokenUtil = new TokenUtil();
        //Initializes the bot
        JDA jdaBot = new JDABuilder(AccountType.BOT).setToken(tokenUtil.loadToken()).buildBlocking();
        jdaBot.addEventListener(this);
        textChannels.addAll(jdaBot.getTextChannels());
        TimeObj timeObj = new TimeObj(this,jdaBot);
        Thread timeThread = new Thread(timeObj);
        timeThread.start();
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent messageEvent) {
        new MessageReceived(messageEvent,this).messageReceivedHandler();
    }
}