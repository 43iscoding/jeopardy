package jeopardy.game.bot;

import jeopardy.game.Config;
import jeopardy.game.Game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/7/16
 * Time: 2:49 PM
 */
public abstract class AbstractBot implements Bot {

    private final Map<String, String> users;
    protected final Game game;

    protected AbstractBot(Game game, Map<String, String> users) {
        this.users = users;
        this.game = game;
    }

    public void sendMessage(Object message) {
        if (Config.LOG_BOT_MESSAGES) {
            System.out.println("Bot: \n" + cleanFormatting(message.toString()));
        }

        if (Config.MUTE_BOT) return;

        sendMessage(message.toString());
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
            game.registerPlayer(realUsers.get(id));
        }
    }

    protected String cleanFormatting(String message) {
        return message;
    }

    protected abstract void sendMessage(String message);

    protected abstract boolean userExists(String userId);

    protected abstract String updateName(String userId, String displayName);

}
