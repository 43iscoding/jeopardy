package jeopardy.game.bot.discord;

import jeopardy.game.Game;
import jeopardy.game.bot.AbstractBot;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.*;

import java.util.HashMap;
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
    private IChannel channel;
    private IGuild guild;

    private final String jeopardyChannelID = "212184742345441280";

    private Queue<String> onReadyMessages = new LinkedList<>();

    private MessageBuilder messageBuilder;

    public DiscordBot(Game game) {
        super(game);
        try {
            client = new ClientBuilder().withToken("MjEyMTc0MTc5MDMyNjI5MjQ4.CooD4w.WQjEnK7N2qNC4JAEGbnDZaHKvVo").login();
            client.getDispatcher().registerListener((IListener<ReadyEvent>)event -> {
                channel = client.getChannelByID(jeopardyChannelID);
                messageBuilder = new MessageBuilder(client).withChannel(channel);
                guild = channel.getGuild();
                for (IUser user : channel.getUsersHere()) {
                    if (!user.getRolesForGuild(guild).stream().anyMatch(r -> r.getName().equals("@player"))) {
                        continue;
                    }

                    users.put(user.getID(), user.getDisplayName(guild));
                }
                registerUsers();
                for (String message : onReadyMessages) {
                    sendMessage(message);
                }
                System.out.println("Discord Bot Connected");
            });
            client.getDispatcher().registerListener((IListener<MessageReceivedEvent>) event -> {
                IMessage message = event.getMessage();
                if (!message.getChannel().equals(channel)) return;

                game.receiveMessage(message.getAuthor().getID(), message.getContent());
            });
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> uniqueMessages = new HashMap<>();

    @Override
    public void sendUniqueMessage(String message, String tag) {
        String messageId = uniqueMessages.get(tag);
        if (messageId != null) {
            IMessage sent = channel.getMessageByID(messageId);
            if (sent != null) {
                RequestBuffer.request(() -> {
                    try {
                        sent.delete();
                    } catch (DiscordException | MissingPermissionsException e) {
                        System.out.println("Error editing message: " + message + " in channel " + channel);
                        e.printStackTrace();
                    }
                });
            }
        }

        if (!client.isReady()) {
            onReadyMessages.offer(message);
            return;
        }
        RequestBuffer.request(() -> {
            try {
                IMessage sent = messageBuilder.withContent(message).build();
                uniqueMessages.put(tag, sent.getID());
            } catch (DiscordException | MissingPermissionsException e) {
                System.out.println("Error on sending message: " + message + " to channel " + channel);
                e.printStackTrace();
            }
        });
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
                System.out.println("Error on sending message: " + message + " to channel " + channel);
                e.printStackTrace();
            }
        });
    }

    @Override
    protected boolean userExists(String userId) {
        return guild.getUserByID(userId) != null;
    }

    @Override
    public String updateName(String userId, String displayName) {
        try {
            IUser user = guild.getUserByID(userId);
            System.out.println("Updating name: " + user.getDisplayName(guild) + " -> " + displayName);

            guild.setUserNickname(user, displayName);
        } catch (MissingPermissionsException | DiscordException | RateLimitException e) {
            e.printStackTrace();
        }
        return displayName;
    }

    @Override
    protected String cleanFormatting(String message) {
        return message.replace(TAG_CODE_BLOCK, "");
    }

    @Override
    public void cleanup() {
        System.out.println("Discord cleanup");
        for (Map.Entry<String, String> entry : users.entrySet()) {
            updateName(entry.getKey(), entry.getValue());
        }
    }
}
