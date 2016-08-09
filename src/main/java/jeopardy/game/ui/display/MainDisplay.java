package jeopardy.game.ui.display;

import jeopardy.game.Game;
import jeopardy.game.Player;
import jeopardy.game.Round;
import jeopardy.game.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by XLIII on 2015-12-23.
 */
public class MainDisplay extends JFrame {

    QuestionSelectionPanel selectionPanel;
    QuestionPanel questionPanel;
    ResultsPanel resultsPanel;
    ThemesPanel themesPanel;
    TaskPanel taskPanel;
    IntroPanel introPanel;

    public MainDisplay(Game game) throws HeadlessException {
        super("Themes");
        selectionPanel = new QuestionSelectionPanel(game);
        questionPanel = new QuestionPanel();
        resultsPanel = new ResultsPanel();
        themesPanel = new ThemesPanel();
        taskPanel = new TaskPanel(game);
        introPanel = new IntroPanel();

        init();
    }

    private void init() {
        setSize(950, 500);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void showIntroPanel() {
        introPanel.init();
        add(introPanel);
        setVisible(true);
    }

    public void startSection(int section, Map<String, List<Round>> rounds) {
        selectionPanel.startSection(rounds);

        showThemesPanel(section, rounds.keySet());
    }

    private void showThemesPanel(int section, Collection<String> themes) {
        themesPanel.init(section, themes);
        add(themesPanel);
        remove(introPanel);
        remove(selectionPanel);
        remove(questionPanel);
        setVisible(true);
    }

    public void showQuestionPanel(Round round) {
        add(questionPanel);
        remove(selectionPanel);
        remove(themesPanel);
        remove(taskPanel);
        setVisible(true);
        questionPanel.setRound(round);
    }

    public void showAnswer() {
        questionPanel.showAnswer();
    }

    public boolean sectionEnded() {
        return selectionPanel.sectionEnded();
    }

    public void showSelectionPanel() {
        add(selectionPanel);
        remove(themesPanel);
        remove(questionPanel);
        setVisible(true);
    }

    public void showResults(List<Player> players) {
        resultsPanel.init(players);
        add(resultsPanel);
        remove(themesPanel);
        remove(questionPanel);
        remove(selectionPanel);
        setVisible(true);
    }

    public String printSelectionPanel() {
        return selectionPanel.printSelectionPanel();
    }

    public void showTaskPanel(Round round) {
        taskPanel.init(round);
        add(taskPanel);
        remove(selectionPanel);
        setVisible(true);
    }
}
