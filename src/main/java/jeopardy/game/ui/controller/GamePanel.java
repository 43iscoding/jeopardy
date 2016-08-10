package jeopardy.game.ui.controller;

import jeopardy.Sound;
import jeopardy.game.Config;
import jeopardy.game.Game;
import jeopardy.game.Player;
import jeopardy.game.Round;
import jeopardy.game.ui.MainController;
import jeopardy.game.ui.PlayerPanel;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XLIII on 2015-12-11.
 */
public class GamePanel extends JPanel {

    private final Game game;
    private final MainController controller;

    private JButton printScoresBtn;
    private JButton endBtn;
    private JButton nextBtn;

    private JButton correctBtn;
    private JButton incorrectBtn;

    private JLabel roundLabel;
    private JLabel playerLabel;
    private JLabel answerLabel;

    private Sound currentSong = new Sound();

    private Map<Player, PlayerPanel> playerPanels = new HashMap<>();

    public GamePanel(final Game game, MainController controller) {
        super();
        this.game = game;
        this.controller = controller;
        init();
    }

    private void init() {
        setLayout(new GridLayout(3, 3));
        setBorder(BorderFactory.createLineBorder(Color.black));

        roundLabel = new JLabel("Round 0", JLabel.CENTER);
        playerLabel = new JLabel("", JLabel.CENTER);
        answerLabel = new JLabel("", JLabel.CENTER);
        answerLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));

        printScoresBtn = new JButton("Print Scores");
        printScoresBtn.addActionListener(e -> {
            game.printScores();
            printScoresBtn.setEnabled(false);
        });

        endBtn = new JButton("End");
        endBtn.addActionListener(e -> {
            endBtn.setEnabled(false);
            game.endRound();

            if (Config.MUSIC_MODE) {
                currentSong.stop();
            }
        });

        nextBtn = new JButton("Next");
        nextBtn.addActionListener(e -> {
            resetPlayers();
            if (Config.MANUAL_SELECTION) {
                nextBtn.setEnabled(false);
                game.showSelectionPanel();
            } else {
                game.nextRound();
            }
        });

        correctBtn = new JButton("Correct");
        correctBtn.addActionListener(e -> {
            game.correct();
        });

        incorrectBtn = new JButton("Incorrect");
        incorrectBtn.addActionListener(e -> {
            game.incorrect();
            //Sound.playSound("Wrong", -10f);
        });
        onNewRound(null);

        add(new JLabel());
        add(roundLabel);
        add(printScoresBtn);
        add(endBtn);
        add(answerLabel);
        add(nextBtn);
        add(correctBtn);
        add(playerLabel);
        add(incorrectBtn);
    }

    public void setThemeSelection() {
        roundLabel.setText("Selecting theme");
        answerLabel.setText(game.lastCorrect.getName());
    }

    public void onThemeList() {
        roundLabel.setText("Theme list");
        answerLabel.setText("");
        nextBtn.setEnabled(true);
    }

    private void setRoundText(Round round) {
        if (round != null) {
            roundLabel.setText(Utils.wrapAndCenter(round.toString()));
            answerLabel.setText(Utils.wrapAndCenter(round.getAnswer()));
        } else {
            roundLabel.setText("");
            answerLabel.setText("");
        }
    }

    public void onNewRound(Round round) {
        controller.syncScore();
        setRoundText(round);
        endBtn.setEnabled(false);
        nextBtn.setEnabled(false);
        correctBtn.setEnabled(false);
        incorrectBtn.setEnabled(false);
        printScoresBtn.setEnabled(false);
        resetPlayers();
    }

    private void initCurrentSong(Round round) {
        currentSong = Sound.playMusic(round);
    }

    public void onRoundEnded(boolean last) {
        endBtn.setEnabled(false);
        endBtn.setText("End");
        playerLabel.setText("");
        nextBtn.setEnabled(true);
        if (last) {
            nextBtn.setText("Results");
        }
        correctBtn.setEnabled(false);
        incorrectBtn.setEnabled(false);
    }

    public void onAnswer(Player player) {
        playerLabel.setText(Utils.wrapAndCenter(player.getName()));
        //startBtn.setEnabled(false);
        nextBtn.setEnabled(false);
        endBtn.setEnabled(false);
        endBtn.setText("End");
        correctBtn.setEnabled(true);
        incorrectBtn.setEnabled(true);
        playerPanels.get(player).answer();

        if (Config.MUSIC_MODE) {
            currentSong.stop();
        }
    }

    public void onRoundStarted(Round round) {
        nextBtn.setEnabled(false);
        playerLabel.setText("");
        correctBtn.setEnabled(false);
        incorrectBtn.setEnabled(false);
        //startBtn.setEnabled(false);
        endBtn.setEnabled(true);

        if (Config.MUSIC_MODE) {
            currentSong.stop();
            initCurrentSong(round);
        }
    }

    public void resetPlayers() {
        for (PlayerPanel player : playerPanels.values()) {
            player.reset();
        }
    }
    public void registerPlayer(Player player) {
        playerPanels.put(player, new PlayerPanel(player, game));
    }

    public void onCorrect(Player player) {
        playerPanels.get(player).correct();
        controller.syncScore();
    }

    public void onReset(Player player) {
        playerPanels.get(player).reset();
        controller.syncScore();
    }

    public void onIncorrect(Player player) {
        playerPanels.get(player).incorrect();
        controller.syncScore();
    }

    public void offerPrintScores() {
        printScoresBtn.setEnabled(true);
    }
}
