package jeopardy.game.ui.controller;

import jeopardy.game.ui.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by XLIII on 2015-12-11.
 */
public class ConfigPanel extends JPanel {

    private JButton initButton;
    private JButton startButton;

    private MainController controller;

    public ConfigPanel(MainController controller) {
        super();
        this.controller = controller;
        init();
    }

    private void init() {
        setLayout(new GridLayout(2, 1));
        setBorder(BorderFactory.createLineBorder(Color.black));


        initButton = new JButton("Initialize");
        initButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(true);
                initButton.setEnabled(false);

                controller.initialize();
            }
        });

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                setVisible(false);

                controller.startGame();
            }
        });
        startButton.setEnabled(false);

        add(initButton);
        add(startButton);
    }

    public void onTutorialStarted() {
        startButton.setEnabled(false);
    }

    public void onTutorialEnded() {
        startButton.setEnabled(true);
    }
}