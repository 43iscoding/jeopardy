package jeopardy.game.bot.discord;

import jeopardy.game.Game;
import jeopardy.game.bot.AbstractBot;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/8/16
 * Time: 2:55 PM
 */
public class DiscordBot extends AbstractBot {

    private IDiscordClient client;
    private IChannel jeopardyChannel;

    private final String jeopardyChannelID = "212184742345441280";


    public DiscordBot(Map<String, String> users) {
        super(users);
        try {
            client = new ClientBuilder().withToken("MjEyMTc0MTc5MDMyNjI5MjQ4.CooD4w.WQjEnK7N2qNC4JAEGbnDZaHKvVo").login();
            client.getDispatcher().registerListener((IListener<ReadyEvent>)event -> {
                jeopardyChannel = client.getChannelByID(jeopardyChannelID);
                System.out.println("Discord Bot Connected");
            });
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sendMessage(String message) {
        try {
            new MessageBuilder(client).withChannel(jeopardyChannel).withContent(message).build();
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean userExists(String userId) {

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String updateName(String userId, String displayName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerListener(Game game) {
        client.getDispatcher().registerListener((IListener<MessageReceivedEvent>) event -> {
            IMessage message = event.getMessage();
            game.receiveMessage(message.getAuthor().getName(), message.getContent());
        });
    }
}
