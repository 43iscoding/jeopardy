package jeopardy.game.bot;

import jeopardy.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/7/16
 * Time: 11:56 PM
 */
public interface Bot {

    void sendMessage(Object message);

    void registerUsers(Game game);

    void registerListener(Game game);

}
