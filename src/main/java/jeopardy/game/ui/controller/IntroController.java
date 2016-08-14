package jeopardy.game.ui.controller;

import jeopardy.game.Player;
import jeopardy.game.ui.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/14/16
 * Time: 1:54 AM
 */
public class IntroController extends JPanel {

    private final MainController controller;

    public IntroController(MainController controller) {
        super();
        this.controller = controller;
    }

    public void init(Collection<Player> players) {
        setLayout(new GridLayout(players.size(), 1));
        setBorder(BorderFactory.createLineBorder(Color.black));
        for (Player player : players) {
            addButton(player);
        }
    }

    private void addButton(final Player player) {
        JButton button = new JButton(player.getName());
        button.addActionListener(e -> controller.introWon(player));
        add(button);
    }
}
