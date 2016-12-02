package jeopardy.game.bot;

import jeopardy.Config;
import jeopardy.game.Game;
import jeopardy.game.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/7/16
 * Time: 2:49 PM
 */
public abstract class AbstractBot implements Bot {

    protected final Map<String, String> users;
    protected final Game game;

    protected AbstractBot(Game game, Map<String, String> users) {
        this.game = game;
        this.users = users;
    }

    protected AbstractBot(Game game) {
        this.game = game;
        this.users = new HashMap<>();
    }

    public void cleanup() {
        System.out.println("Cleanup");
    }

    @Override
    public void updatePlayerName(Player player) {
        String baseName = users.get(player.getUserId());
        updateName(player.getUserId(), baseName + " (" + player.getScore() + ")");
    }

    public void sendMessage(Object message) {
        if (Config.LOG_BOT_MESSAGES) {
            System.out.println("Bot: \n" + cleanFormatting(message.toString()));
        }

        if (Config.MUTE_BOT) return;

        sendMessage(message.toString());
    }

    @Override
    public void sendUniqueMessage(Object message, String id) {
        if (Config.LOG_BOT_MESSAGES) {
            System.out.println("Bot: \n" + cleanFormatting(message.toString()));
        }

        if (Config.MUTE_BOT) return;

        sendUniqueMessage(message.toString(), id);
    }

    protected void registerUsers() {
        Map<String, String> realUsers = new HashMap<>();
        for (String userId : users.keySet()) {
            if (!userExists(userId)) {
                System.out.println("Warning! User " + userId + " does not exist in current chat");
                continue;
            }

            String displayName = updateName(userId, users.get(userId));

            realUsers.put(userId, displayName);
        }

        for (String id : users.keySet()) {
            if (!realUsers.containsKey(id)) {
                System.out.println("No real user found with id: " + id);
                continue;
            }
            game.registerPlayer(id, realUsers.get(id));
        }
    }

    protected String cleanFormatting(String message) {
        return message;
    }

    protected abstract void sendMessage(String message);

    protected void sendUniqueMessage(String message, String id) {
        sendMessage(message);
    }

    protected abstract boolean userExists(String userId);

    public abstract String updateName(String userId, String displayName);

}
