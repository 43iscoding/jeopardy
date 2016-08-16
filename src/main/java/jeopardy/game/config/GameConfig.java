package jeopardy.game.config;

import java.util.ArrayList;
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

    public GameConfig(IntroConfig intro, List<ThemeConfig> themes) {
        this.intro = intro;
        this.themes = themes;
    }

    public GameConfig(IntroConfig intro, List<ThemeConfig> themes, int count) {
        this.intro = intro;
        this.themes = themes.subList(0, count);
    }

    public GameConfig(IntroConfig intro, List<ThemeConfig> themes, int ... indices) {
        this.intro = intro;
        this.themes = new ArrayList<>();
        for (int i : indices) {
            this.themes.add(themes.get(i));
        }
    }
}
