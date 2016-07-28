package jeopardy.game.ui;

import jeopardy.game.Game;
import jeopardy.game.Player;
import jeopardy.game.Round;

import javax.swing.*;
import java.awt.*;
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

    public MainDisplay(Game game) throws HeadlessException {
        super("Themes");
        selectionPanel = new QuestionSelectionPanel(game);
        questionPanel = new QuestionPanel();
        resultsPanel = new ResultsPanel();
        themesPanel = new ThemesPanel();

        init();
    }

    private void init() {
        setSize(950, 500);
    }

    public void startSection(int section, Map<String, List<Round>> rounds) {
        selectionPanel.startSection(rounds);

        showThemesPanel(section, rounds.keySet());
    }

    private void showThemesPanel(int section, Collection<String> themes) {
        themesPanel.init(section, themes);
        add(themesPanel);
        remove(selectionPanel);
        remove(questionPanel);
        setVisible(true);
    }

    public void showQuestionPanel(Round round) {
        add(questionPanel);
        remove(selectionPanel);
        remove(themesPanel);
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
}
