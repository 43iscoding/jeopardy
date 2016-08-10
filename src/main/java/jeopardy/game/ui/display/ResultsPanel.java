package jeopardy.game.ui.display;

import jeopardy.game.Player;
import jeopardy.game.utils.Colors;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by XLIII on 2016-01-17.
 */
public class ResultsPanel extends JPanel {

    public void init(List<Player> players) {
        setBackground(Colors.backgroundBlue);
        setLayout(new GridLayout(players.size(), 1));
        setSize(950, 500);

        for (int i = 0; i < players.size(); i++) {
            add(createPlayerLabel(players.get(i), i + 1));
        }
        setVisible(true);
    }

    private Component createPlayerLabel(Player player, int i) {
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setFont(Utils.getJeopardyFont(Font.BOLD, 50));
        label.setForeground(getColor(i));
        label.setText(i + ". " + player);
        return label;
    }

    private Color getColor(int place) {
        switch (place) {
            case 1: return Colors.gold;
            case 2: return Colors.silver;
            case 3: return Colors.bronze;
            default: return Colors.themeWhite;
        }
    }
}
