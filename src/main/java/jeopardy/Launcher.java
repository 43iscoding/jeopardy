package jeopardy;

import jeopardy.game.ui.MainController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Launcher {

    public static Map<String, String> PLAYERS = new HashMap<String, String>() {{
        //STUPID BOTS
        put("miss.gf54", "ZeGoattt");
        put("miss.gf53", "BizBony");
        put("miss.gf52", "MissCoookiez");

        // KRABE CHAT
        //put("sara_michael_pb", "KateiChg");
        //put("armen66642", "Армен");
        //put("sergleo_21213", "Сергей");
        //put("ihtiandrei", "IhtiAndrei");
        //put("phantomscadman", "Phantom");
        //put("bogps123", "Oleg");

        //MISC
        //put("angel_eyes57", "Dashutka");
    }};

    public static List<String> THEMES = new ArrayList<String>() {{
        add("DISNEY");
        add("COLORS");
        add("TV SERIES 1");
        add("CLASSICAL");
        add("MOVIES 1");

        add("CHEMICAL");
        add("MOVIES 2");
        add("TV SERIES 2");
        add("NUMBERS");
        add("GAMES 1");

        add("GAMES 2");
        add("DANK MEMES");
        add("THEME 14");
        add("MOVIES 3");
        add("THEME 15");
    }};

    public static void main(String[] args) {
        new MainController();
    }
}
