package jeopardy.game;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Player implements Comparable<Player> {

    public static int INDEX = 0;

    private final String userId;
    private final int index;
    private final String name;
    private int score;
    //last delta
    private int delta;

    public Player(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.index = ++INDEX;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int delta) {
        this.delta = delta;
        this.score += delta;
        Game.updatePlayerName(this);
    }

    public int getIndex() {
        return index;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public int compareTo(Player o) {
        return o.getScore() - getScore();
    }

    private String signed(int value) {
        return (value >= 0 ? "+" : "") + value;
    }

    public String getScoreMessage() {
        return name + " " + signed(delta) + " [" + score + "]";
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return index == player.index;

    }

    @Override
    public int hashCode() {
        return index;
    }
}
