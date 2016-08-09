package jeopardy.game.ui.display;

import jeopardy.game.Round;
import jeopardy.game.utils.Colors;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by XLIII on 2015-12-23.
 */
public class QuestionPanel extends Panel {

    private JLabel themeLabel;
    private JLabel costLabel;
    private JLabel answerLabel;

    public QuestionPanel() {
        init();
    }

    private void init() {
        setSize(950, 500);
        setBackground(Colors.backgroundBlue);
        setLayout(new GridLayout(3, 1));

        themeLabel = new JLabel("THEME", JLabel.CENTER);
        themeLabel.setFont(Utils.getJeopardyFont(Font.BOLD, 80));
        themeLabel.setForeground(Colors.themeWhite);
        add(themeLabel);

        costLabel = new JLabel("100", JLabel.CENTER);
        costLabel.setFont(Utils.getJeopardyFont(Font.BOLD, 60));
        costLabel.setForeground(Colors.roundYellow);
        add(costLabel);

        answerLabel = new JLabel("Answer", JLabel.CENTER);
        answerLabel.setFont(Utils.getJeopardyFont(Font.BOLD, 50));
        answerLabel.setForeground(Colors.answerWhite);
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
