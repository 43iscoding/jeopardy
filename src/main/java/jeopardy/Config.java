package jeopardy;

import jeopardy.game.bot.BotType;

/**
 * Created by XLIII on 2015-12-12.
 */
public class Config {

    public static final int THEMES_PER_SECTION = 5;
    public static final int ROUND_PER_THEME = 5;
    public static final int SCORE_MULTIPLIER = 100;

    public static final boolean LOG_BOT_MESSAGES = true;

    public static final boolean TUTORIAL_ENABLED = false;

    public static final boolean FORCE_NEW_CHAT = false;

    public static final boolean MUSIC_MODE = true;

    public static final boolean PLAYER_PANEL_CIRCLE = false;

    public static final boolean MANUAL_SELECTION = true;

    public static final boolean ROUND_MULTIPLICATOR = true;

    public static final boolean USE_VIRTUAL_AUDIO = true;

    public static final boolean MUTE_BOT = true;

    public static final boolean BEAUTIFY = true; //Welcome messages, chat topic etc.

    public static final BotType BOT = BotType.DISCORD;

    public static final boolean ALLOW_TIE = true;
}
