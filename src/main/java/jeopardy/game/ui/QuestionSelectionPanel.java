package jeopardy.game.ui;

import jeopardy.game.Config;
import jeopardy.game.Game;
import jeopardy.game.Round;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * Created by XLIII on 2015-12-21.
 */
public class QuestionSelectionPanel extends JPanel {

    private Color backgroundBlue = new Color(0, 0, 177);
    private Color themeWhite = new Color(155, 192, 255);
    private Color roundYellow = new Color(225, 175, 123);

    private Game game;

    private int counter;

    public QuestionSelectionPanel(Game game) throws HeadlessException {
        this.game = game;
        init();
    }

    private void init() {
        setLayout(new GridLayout(Config.ROUND_PER_THEME + 1, Config.THEMES_PER_SECTION));
        setSize(950, 500);
        setVisuals();

        setVisible(true);
    }

    public boolean sectionEnded() {
        return counter == 0;
    }

    void startSection(Map<String, List<Round>> rounds) {
        removeAll();
        for (String theme : rounds.keySet()) {
            add(createThemeLabel(theme));
        }

        int index = 0;
        while (true) {
            for (List<Round> theme : rounds.values()) {
                if (theme.size() <= index) return;

                add(createRoundButton(theme.get(index)));
            }
            index++;
        }
    }

    private void setVisuals() {
        setBackground(Color.black);
    }

    public JButton createRoundButton(final Round round) {
        final JButton button = new JButton(round.getScore() + "");
        button.setFont(Utils.getJeopardyFont(Font.BOLD, 50));
        button.setBackground(backgroundBlue);
        button.setBorder(new LineBorder(Color.black, 4));
        button.setForeground(roundYellow);
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startRound(round);
                button.setEnabled(false);
                button.setText("");
                game.showQuestionPanel(round);
                counter--;
            }
        });
        counter++;
        return button;
    }

    public JLabel createThemeLabel(String theme) {
        JLabel label = new JLabel(Utils.wrapAndCenter(theme), JLabel.CENTER);
        label.setFont(new Font("Century", Font.BOLD, 10));
        label.setFont(Utils.getJeopardyFont(Font.PLAIN, 30));
        label.setBackground(backgroundBlue);
        label.setBorder(new LineBorder(Color.black, 4));
        label.setForeground(themeWhite);
        label.setOpaque(true);
        return label;
    }
}
