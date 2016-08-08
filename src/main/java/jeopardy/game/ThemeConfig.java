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

    private final String containsTask = "Either band or song name contains ";

    public String name;
    public List<RoundConfig> rounds;
    public String task;

    public ThemeConfig(String name, RoundConfig... rounds) {
        this.name = name;
        this.rounds = Arrays.asList(rounds);
    }

    public ThemeConfig task(String task) {
        this.task = task;
        return this;
    }

    public ThemeConfig containsTask(String subject) {
        return task(containsTask + subject);
    }
}
