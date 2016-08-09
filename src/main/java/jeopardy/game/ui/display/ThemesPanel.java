package jeopardy.game.ui.display;

import jeopardy.game.utils.Colors;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * Created by XLIII on 2016-01-17.
 */
public class ThemesPanel extends JPanel {

    public void init(int section, Collection<String> themes) {
        setLayout(new GridLayout(themes.size() + 4, 1));
        setSize(950, 500);
        setVisible(true);
        setBackground(Colors.backgroundBlue);
        removeAll();
        add(new JLabel());
        add(headerLabel(section));
        add(new JLabel());
        for (String theme : themes) {
            add(themeLabel(theme));
        }
        add(new JLabel());
    }

    private Component headerLabel(int section) {
        JLabel label = new JLabel("ROUND " + section, JLabel.CENTER);
        label.setFont(Utils.getJeopardyFont(Font.BOLD, 55));
        label.setForeground(Colors.roundYellow);
        return label;
    }

    private Component themeLabel(String theme) {
        JLabel label = new JLabel(theme, JLabel.CENTER);
        label.setFont(Utils.getJeopardyFont(Font.BOLD, 45));
        label.setForeground(Colors.themeWhite);
        return label;
    }
}
