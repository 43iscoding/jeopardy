package jeopardy.game.config;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 7/29/16
 * Time: 1:28 AM
 */
public class GameConfig {

    public IntroConfig intro;
    public List<ThemeConfig> themes;

    public GameConfig(IntroConfig intro, ThemeConfig... themes) {
        this.intro = intro;
        this.themes = Arrays.asList(themes);
    }
}
