package jeopardy.game.bot;

import jeopardy.game.Player;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/7/16
 * Time: 11:56 PM
 */
public interface Bot {

    void sendMessage(Object message);

    void sendUniqueMessage(Object message, String id);

    void cleanup();

    void updatePlayerName(Player player);
}
