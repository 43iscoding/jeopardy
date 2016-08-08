package jeopardy.game.ui;

import jeopardy.Launcher;
import jeopardy.game.Config;
import jeopardy.game.Game;
import jeopardy.game.Player;
import jeopardy.game.bot.discord.DiscordBot;
import jeopardy.game.bot.skype.SkypeBot;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by XLIII on 2015-12-10.
 */
public class MainController {

    private static String COLOR_TAG = "<font color=#B12625>%s</font>";

    private JFrame frame;
    private JLabel header;
    private JLabel warningLabel;
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
        frame.setLayout(new GridLayout(3, 1));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        header = new JLabel("Jeopardy Skype bot by XLIII", JLabel.CENTER);

        warningLabel = new JLabel("", JLabel.CENTER);

        configPanel = new ConfigPanel(this);

        frame.add(header);
        frame.add(warningLabel);
        frame.add(configPanel);
        frame.setVisible(true);
    }

    public void startGame() {
        frame.remove(configPanel);
        frame.add(gamePanel);
        game.start();
    }

    public void initialize() {
        game = new Game(Launcher.cfg);
        game.setController(this);
        gamePanel = new GamePanel(game, this);
        game.setPanel(gamePanel);
        initBot();
        header.setText(game.getConfigStr());
        System.out.println(game.getConfigStr());
        if (Config.TUTORIAL_ENABLED) {
            game.startTutorial();
        }

        if (Config.BEAUTIFY) {
            game.sendMessage("Welcome to Jeopardy!");
        }
    }

    private void initBot() {
        switch (Config.BOT) {
            case DISCORD:
                game.setBot(new DiscordBot(game, Launcher.DISCORD_PLAYERS));
                break;
            case SKYPE:
                game.setBot(new SkypeBot(game, Launcher.SKYPE_PLAYERS));
                break;
        }
    }

    public void onGameComplete(Player winner) {
        gamePanel.setVisible(false);
        StringBuilder sb = new StringBuilder("Congratulations to ").append(winner.getName()).append("!");
        sb.append("<br>").append("<br>");
        sb.append(game.getPrintScoresString(true));
        header.setText(Utils.wrapAndCenter(sb.toString()));
    }

    public void onTutorialStarted() {
        configPanel.onTutorialStarted();
    }

    public void onTutorialEnded() {
        configPanel.onTutorialEnded();
    }

    public void onTutorialProgress(int remaining) {
        header.setText("Tutorial in progress: " + (Game.Players() - remaining) + "/" + Game.Players());
    }

    public void syncScore() {
        header.setText(Utils.wrapAndCenter(game.getPrintScoresString(true)));
    }

    public void setWarningText(String text) {
        warningLabel.setText(Utils.wrapAndCenter(String.format(COLOR_TAG, text)));
    }
}

