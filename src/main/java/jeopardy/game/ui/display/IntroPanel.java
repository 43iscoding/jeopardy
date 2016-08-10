package jeopardy.game.ui.display;

import jeopardy.game.config.IntroConfig;
import jeopardy.game.utils.Colors;
import jeopardy.game.utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/10/16
 * Time: 10:03 PM
 */
public class IntroPanel extends JPanel {

    private JLabel question;
    private JLabel answer;

    public IntroPanel() {
        setSize(950, 500);
        setBackground(Colors.backgroundBlue);
        setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("INTRO QUESTION", JLabel.CENTER);
        label.setFont(Utils.getJeopardyFont(Font.PLAIN, 80));
        label.setForeground(Colors.themeWhite);
        add(label);

        question = new JLabel("QUESTION", JLabel.CENTER);
        question.setFont(Utils.getJeopardyFont(Font.PLAIN, 60));
        question.setForeground(Colors.roundYellow);
        add(question);

        answer = new JLabel("ANSWER", JLabel.CENTER);
        answer.setFont(Utils.getJeopardyFont(Font.BOLD, 70));
        answer.setForeground(Colors.answerWhite);
        answer.setVisible(false);
        add(answer);
    }

    public void showAnswer() {
        answer.setVisible(true);
    }

    public void setIntro(IntroConfig intro) {
        this.question.setText(Utils.wrapAndCenter(intro.question));
        this.answer.setText(Utils.wrapAndCenter(intro.answer));
        this.answer.setVisible(false);
    }
}
