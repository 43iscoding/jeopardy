package jeopardy.game;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 7/29/16
 * Time: 1:28 AM
 */
public class RoundConfig {

    private final String COLOR_TAG = "<font color=#E1AF53>%s</font>";
    private final String IGNORE_CASE = "(?i)";

    private String artist;
    private String song;
    private String path;

    public RoundConfig(String song, String path) {
        this.song = song;
        this.path = path;
    }

    public RoundConfig(String artist, String song, String path) {
        this.artist = artist;
        this.song = song;
        this.path = path;
    }

    public RoundConfig highlight(String pattern) {
        if (artist != null) {
            artist = artist.replaceAll(IGNORE_CASE + pattern, String.format(COLOR_TAG, pattern));
        }
        song = song.replaceAll(IGNORE_CASE + pattern, String.format(COLOR_TAG, pattern));
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
}
