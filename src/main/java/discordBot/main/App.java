

package discordBot.main;
import javax.security.auth.login.LoginException;

import discordBot.main.botIO.MessageReceived;
import discordBot.main.outputWindow.ChannelManager;
import discordBot.main.outputWindow.TradingChannelObject;
import discordBot.tokenUtil.TokenUtil;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

public class App extends ListenerAdapter {
    public static ArrayList<TextChannel> textChannels = new ArrayList<TextChannel>();
    public static ArrayList<TradingChannelObject> tradingChannelObjects = new ArrayList<TradingChannelObject>();
    public static ChannelManager channelManager = new ChannelManager();
    public static void main(String[] args) throws LoginException, IllegalArgumentException, InterruptedException    {
        //loads token loading class
        TokenUtil tokenUtil = new TokenUtil();
        //Initializes the bot
        JDA jdaBot = new JDABuilder(AccountType.BOT).setToken(tokenUtil.loadToken()).buildBlocking();
        jdaBot.addEventListener(new App());
        jdaBot.getPresence().setGame(Game.of(Game.GameType.DEFAULT ,"for available commands do .Commands"));
        textChannels.addAll(jdaBot.getTextChannels());
        //creates trading channels
        channelManager.initiateTradingChannels();

    }
    @Override
    public void onMessageReceived(MessageReceivedEvent messageEvent) {
        new MessageReceived(messageEvent,this).messageReceivedHandler();
    }

}