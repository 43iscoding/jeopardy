package jeopardy.game.ui;

import jeopardy.game.Round;

import javax.swing.*;
import java.awt.*;

/**
 * Created by XLIII on 2015-12-23.
 */
public class QuestionPanel extends Panel {

    private Color backgroundBlue = new Color(0, 0, 177);
    private Color themeWhite = new Color(155, 192, 255);
    private Color roundYellow = new Color(225, 175, 123);
    private Color answerWhite = new Color(220 ,220, 255);

    private JLabel themeLabel;
    private JLabel costLabel;
    private JLabel answerLabel;

    public QuestionPanel() {
        init();
    }

    private void init() {
        setSize(950, 500);
        setBackground(backgroundBlue);
        setLayout(new GridLayout(3, 1));

        themeLabel = new JLabel("THEME", JLabel.CENTER);
        themeLabel.setFont(Utils.getJeopardyFont(Font.BOLD, 80));
        themeLabel.setForeground(themeWhite);
        add(themeLabel);

        costLabel = new JLabel("100", JLabel.CENTER);
        costLabel.setFont(Utils.getJeopardyFont(Font.BOLD, 60));
        costLabel.setForeground(roundYellow);
        add(costLabel);

        answerLabel = new JLabel("Answer", JLabel.CENTER);
        answerLabel.setFont(Utils.getJeopardyFont(Font.BOLD, 50));
        answerLabel.setForeground(answerWhite);
        answerLabel.setVisible(false);
        add(answerLabel);
    }

    public void showAnswer() {
        answerLabel.setVisible(true);
    }

    public void setRound(Round round) {
        themeLabel.setText(Utils.wrapAndCenter(round.getTheme()));
        costLabel.setText(Utils.wrapAndCenter(round.getScore() + ""));
        answerLabel.setText(Utils.wrapAndCenter(round.getAnswer()));
        answerLabel.setVisible(false);
    }
}
