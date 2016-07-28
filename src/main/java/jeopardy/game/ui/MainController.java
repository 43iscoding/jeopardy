package jeopardy.game.ui;

import jeopardy.Bot;
import jeopardy.Launcher;
import jeopardy.game.Config;
import jeopardy.game.Game;
import jeopardy.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by XLIII on 2015-12-10.
 */
public class MainController {
    private JFrame frame;
    private JLabel header;
    private ConfigPanel configPanel;
    private GamePanel gamePanel;

    private Game game;

    public MainController() {
        prepareGUI();
    }

    private void prepareGUI() {
        frame = new JFrame("Jeopardy Bot");
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(2, 1));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        header = new JLabel("Jeopardy Skype bot by XLIII", JLabel.CENTER);

        configPanel = new ConfigPanel(this);

        frame.add(header);
        frame.add(configPanel);
        frame.setVisible(true);
    }

    public void startGame() {
        System.out.println("Starting game");
        frame.remove(configPanel);
        frame.add(gamePanel);
        game.start();
    }

    public void initialize() {
        game = new Game(Launcher.cfg);
        game.setController(this);
        gamePanel = new GamePanel(game, this);
        game.setPanel(gamePanel);
        game.setBot(new Bot(Launcher.PLAYERS, game));
        header.setText(game.getConfigStr());
        System.out.println(game.getConfigStr());
        if (Config.TUTORIAL_ENABLED) {
            game.startTutorial();
        }
        game.sendMessage("Welcome to Jeopardy!");
    }

    public void onGameComplete(Player winner) {
        gamePanel.setVisible(false);
        StringBuilder sb = new StringBuilder("Congratulations to ").append(winner.getName()).append("!");
        sb.append("<br>").append("<br>");
        sb.append(game.getPrintScoresString(true, true));
        header.setText(Utils.wrapAndCenter(sb.toString()));
    }

    public void onTutorialStarted() {
        configPanel.onTutorialStarted();
    }

    public void onTutorialEnded() {
        configPanel.onTutorialEnded();
    }

    public void onTutorialProgress(int remaining, int total) {
        header.setText("Tutorial in progress: " + (total - remaining) + "/" + total);
    }

    public void syncScore() {
        header.setText(Utils.wrapAndCenter(game.getPrintScoresString(true, true)));
    }
}

