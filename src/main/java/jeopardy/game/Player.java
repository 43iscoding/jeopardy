package jeopardy.game;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Player implements Comparable<Player> {

    public static int ID = 0;

    private int id;
    private String name;
    private int score;
    //last delta
    private int delta;

    public Player(String name) {
        this.name = name;
        this.id = ++ID;
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
    }

    public int getId() {
        return id;
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

        return id == player.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
