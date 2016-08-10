package jeopardy.game.config;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/10/16
 * Time: 9:40 PM
 */

/**
 * Question before the game to determine the player to make the first choice
 * Answer should be integer
 */
public class IntroConfig {

    public String question;
    public int answer;

    public IntroConfig(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }
}
