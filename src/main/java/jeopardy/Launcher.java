package jeopardy;

import jeopardy.game.config.GameConfig;
import jeopardy.game.ui.MainController;
import jeopardy.volume.NameThatTuna1;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Launcher {

    public static Map<String, String> SKYPE_PLAYERS = new LinkedHashMap<String, String>() {{
        //STUPID BOTS
        //put("miss.gf54", "ZeGoattt");
        //put("miss.gf53", "BizBony");
        //put("miss.gf52", "MissCoookiez");

        // KRABE CHAT
        //put("sara_michael_pb", "KateiChg"); PLAYED
        //put("armen66642", "Армен");
        //put("sergleo_21213", "Сергей");
        //put("ihtiandrei", "IhtiAndrei");
        //put("phantomscadman", "Phantom");
        //put("dark_doctor", "Dark Doctor");
        //put("bogps123", "Oleg");
        //put("feakalakvendi", "Alexandr");
        //put("leinaps", "Leinaps");

        //put("quietguest123", "Nickolay"); PLAYED



        //MISC

        //put("lotivecsorks", "VitBuk"); //PLAYED
        //put("arsenicum91", "Shurara"); //PLAYED
        //put("angel_eyes57", "Dashutka"); //PLAYED
    }};

    public static Map<String, String> DISCORD_PLAYERS = new LinkedHashMap<String, String>() {{
        //STUPID BOTS
        //put("miss.gf54", "ZeGoattt");
        //put("miss.gf53", "BizBony");
        //put("miss.gf52", "MissCoookiez");

        // KRABE CHAT
        //put("sara_michael_pb", "KateiChg"); PLAYED
        //put("armen66642", "Армен");
        //put("sergleo_21213", "Сергей");
        //put("ihtiandrei", "IhtiAndrei");
        //put("phantomscadman", "Phantom");
        //put("dark_doctor", "Dark Doctor");
        //put("bogps123", "Oleg");
        //put("feakalakvendi", "Alexandr");
        //put("leinaps", "Leinaps");

        //put("quietguest123", "Nickolay"); PLAYED



        //MISC

        put("VitBuk", "VitBuk"); //PLAYED
        put("XLIII", "XLIII"); //PLAYED
        put("overseer", "overseer");
    }};

    public static GameConfig cfg = new NameThatTuna1();

    public static void main(String[] args) {
        new MainController();
    }
}
