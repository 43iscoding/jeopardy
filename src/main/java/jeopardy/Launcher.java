package jeopardy;

import jeopardy.game.config.GameConfig;
import jeopardy.game.ui.MainController;
import jeopardy.volume.NameThatTuna;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Launcher {

    public static Map<String, String> SKYPE_PLAYERS = new LinkedHashMap<>();

    public static GameConfig cfg = NameThatTuna.production();//NameThatTuna.themes(10,11,12,13,14);

    public static void main(String[] args) {
        new MainController();
    }
}
