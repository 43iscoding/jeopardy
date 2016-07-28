package jeopardy.game;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 7/29/16
 * Time: 1:29 AM
 */
public class ThemeConfig {

    public String name;
    public List<RoundConfig> rounds;

    public ThemeConfig(String name, RoundConfig... rounds) {
        this.name = name;
        this.rounds = Arrays.asList(rounds);
    }
}
