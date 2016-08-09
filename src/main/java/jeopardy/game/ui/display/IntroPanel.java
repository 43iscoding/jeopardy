package jeopardy.game.ui.display;

import jeopardy.game.utils.Colors;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/9/16
 * Time: 6:27 PM
 */
public class IntroPanel extends JPanel {

    public IntroPanel() {
        setSize(950, 500);
        setBackground(Colors.backgroundBlue);
        setLayout(new GridLayout(1, 1));

        JLabel label = new JLabel("NAME THAT TUNA", JLabel.CENTER);
        label.setFont(Utils.getJeopardyFont(Font.BOLD, 80));
        label.setForeground(Colors.roundYellow);
        add(label);
    }
}
