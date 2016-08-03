package jeopardy.game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Round {

    private String theme;
    private String answer;
    private String path;
    private int score;

    private float volume;

    private Queue<Player> pending = new LinkedList<>();
    private Set<Player> wrong = new HashSet<>();
    private Set<Player> right = new HashSet<>();

    private boolean manualEnded;

    private boolean started;

    public Round(String theme, int score, RoundConfig cfg) {
        this.theme = theme;
        this.score = score;
        this.answer = cfg.fullName();
        this.path = cfg.getPath();
        this.volume = cfg.getVolume();
    }

    public boolean ended() {
        return manualEnded || !right.isEmpty() || everyoneAnswered();
    }

    public boolean started() {
        return started;
    }

    public void start() {
        started = true;
    }

    public void end() {
        manualEnded = true;
    }

    public float getVolume() {
        return volume;
    }

    public boolean offer(Player player) {
        if (right.contains(player)) return false;

        if (wrong.contains(player)) return false;

        if (pending.contains(player)) return false;

        boolean empty = pending.isEmpty();
        return pending.offer(player) && empty;
    }

    public boolean hasPendingPlayers() {
        return !pending.isEmpty();
    }

    public int wrongPlayers() {
        return wrong.size();
    }

    public boolean everyoneAnswered() {
        return wrong.size() + right.size() == Game.Players();
    }

    @Override
    public String toString() {
        return theme + " (" + score + ")";
    }

    public Player incorrect() {
        Player player = pending.poll();
        player.addScore(-score);
        wrong.add(player);
        return player;
    }

    public Player correct() {
        Player player = pending.poll();
        player.addScore(score);
        right.add(player);
        return player;
    }

    public String toMusicPath() {
        return path;
    }

    public Player pendingPlayer() {
        return pending.peek();
    }

    public boolean last() {
        if (Config.MANUAL_SELECTION) return false;
        return score == Config.SCORE_MULTIPLIER * Config.ROUND_PER_THEME;
    }

    public boolean playerWrong(Player player) {
        return wrong.contains(player);
    }

    public void wrongToRight(Player player) {
        wrong.remove(player);
        player.addScore(score);
        right.add(player);
        player.addScore(score);
    }

    public boolean playerRight(Player player) {
        return right.contains(player);
    }

    public void rightToWrong(Player player) {
        right.remove(player);
        player.addScore(-score);
        wrong.add(player);
        player.addScore(-score);
    }

    public int getScore() {
        return score;
    }

    public String getTheme() {
        return theme;
    }

    public String getAnswer() {
        return answer;
    }

    public String getPath() {
        return path;
    }
}
