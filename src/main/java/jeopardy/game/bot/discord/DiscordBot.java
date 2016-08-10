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
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.*;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/8/16
 * Time: 2:55 PM
 */
public class DiscordBot extends AbstractBot {

    public static final String TAG_CODE_BLOCK = "```";

    private IDiscordClient client;
    private IChannel jeopardyChannel;

    private final String jeopardyChannelID = "212184742345441280";

    private Queue<String> onReadyMessages = new LinkedList<>();

    private MessageBuilder messageBuilder;


    public DiscordBot(Game game, Map<String, String> users) {
        super(game, users);
        try {
            client = new ClientBuilder().withToken("MjEyMTc0MTc5MDMyNjI5MjQ4.CooD4w.WQjEnK7N2qNC4JAEGbnDZaHKvVo").login();
            client.getDispatcher().registerListener((IListener<ReadyEvent>)event -> {
                jeopardyChannel = client.getChannelByID(jeopardyChannelID);
                messageBuilder = new MessageBuilder(client).withChannel(jeopardyChannel);
                registerUsers();
                for (String message : onReadyMessages) {
                    sendMessage(message);
                }
                System.out.println("Discord Bot Connected");
            });
            client.getDispatcher().registerListener((IListener<MessageReceivedEvent>) event -> {
                IMessage message = event.getMessage();
                if (!message.getChannel().equals(jeopardyChannel)) return;

                game.receiveMessage(message.getAuthor().getName(), message.getContent());
            });
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sendMessage(String message) {
        if (!client.isReady()) {
            onReadyMessages.offer(message);
            return;
        }
        RequestBuffer.request(() -> {
            try {
                messageBuilder.withContent(message).build();
            } catch (DiscordException | MissingPermissionsException e) {
                System.out.println("Error on sending message: " + message + " to channel " + jeopardyChannel);
                e.printStackTrace();
            }

        });


    }

    @Override
    protected boolean userExists(String userId) {
        for (IUser user : jeopardyChannel.getUsersHere()) {
            if (user.getName().equals(userId)) return true;
        }
        return false;
    }

    @Override
    protected String updateName(String userId, String displayName) {
        return displayName;
    }

    @Override
    protected String cleanFormatting(String message) {
        return message.replace(TAG_CODE_BLOCK, "");
    }
}
