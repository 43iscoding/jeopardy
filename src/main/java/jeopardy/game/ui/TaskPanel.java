package jeopardy.game.ui;

import jeopardy.game.Game;
import jeopardy.game.Round;
import jeopardy.game.utils.Colors;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/9/16
 * Time: 1:24 AM
 */
public class TaskPanel extends JPanel {

    private Game game;

    public TaskPanel(Game game) {
        this.game = game;
    }

    public void init(Round round) {
        setLayout(new GridLayout(3, 1));
        setSize(950, 500);
        setVisible(true);
        setBackground(Colors.backgroundBlue);
        removeAll();
        add(createButton(round));
        add(themeLabel(round.getTask()));
        add(new JLabel());
    }

    private Component themeLabel(String theme) {
        JLabel label = new JLabel(theme, JLabel.CENTER);
        label.setFont(Utils.getJeopardyFont(Font.PLAIN, 50));
        label.setForeground(Colors.roundYellow);
        return label;
    }

    private JButton createButton(final Round round) {
        final JButton button = new JButton(round.getTheme() + "");
        button.setFont(Utils.getJeopardyFont(Font.BOLD, 70));
        button.setBackground(Colors.backgroundBlue);
        button.setForeground(Colors.themeWhite);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.addActionListener(e -> {

            game.startRound(round);
        });
        return button;
    }
}
