package jeopardy.game;

import jeopardy.game.utils.Utils;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 7/29/16
 * Time: 1:28 AM
 */
public class RoundConfig {

    private final Color highlightColor = new Color(225, 175, 83);
    private final String IGNORE_CASE = "(?i)";

    private String artist;
    private String song;
    private String path;
    private float volume;

    public RoundConfig(String song, String path) {
        this.song = song;
        this.path = path;
    }

    public RoundConfig(String artist, String song, String path) {
        this.artist = artist;
        this.song = song;
        this.path = path;
    }

    public RoundConfig volume(float volume) {
        this.volume = volume;
        return this;
    }

    public RoundConfig highlight(String pattern) {
        if (artist != null) {
            artist = artist.replaceAll(IGNORE_CASE + pattern, Utils.htmlColored(pattern, Utils.toHex(highlightColor)));
        }
        song = song.replaceAll(IGNORE_CASE + pattern, Utils.htmlColored(pattern, Utils.toHex(highlightColor)));
        return this;
    }

    public String fullName() {
        String a;
        if (artist == null || artist.isEmpty()) {
            a = "";
        } else {
            a = artist + " - ";
        }
        return a + song;
    }

    public String getPath() {
        return path;
    }

    public float getVolume() {
        return volume;
    }
}
