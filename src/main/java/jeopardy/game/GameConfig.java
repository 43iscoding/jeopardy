package jeopardy.game;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 7/29/16
 * Time: 1:28 AM
 */
public class GameConfig {
    List<ThemeConfig> themes;

    public GameConfig(ThemeConfig... themes) {
        this.themes = Arrays.asList(themes);
    }
}
