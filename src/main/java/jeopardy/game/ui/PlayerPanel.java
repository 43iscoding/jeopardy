package jeopardy.game.ui;

import jeopardy.game.Config;
import jeopardy.game.Game;
import jeopardy.game.Player;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by XLIII on 2015-12-12.
 */
public class PlayerPanel extends JFrame {

    public Game game;
    private Player player;
    private JPanel light;

    public PlayerPanel(Player player) throws HeadlessException {
        super("Player" + player.getId());
        this.player = player;
        init();
    }

    public PlayerPanel(Player player, Game game) throws HeadlessException {
        super("Player" + player.getId());
        this.game = game;
        this.player = player;
        init();
    }

    private Color defaultColor;
    private Color wrongColor = Color.red;
    private Color rightColor = Color.green;
    private Color answeringColor = Color.blue;

    private void init() {
        setSize(200, 200);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
        setBackground(Color.gray);

        light = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(light.getBackground());
                if (Config.PLAYER_PANEL_CIRCLE) {
                    g.fillOval(getX(), getY(), getWidth(), getHeight());
                } else {
                    g.fillRect(getX(), getY(), getWidth(), getHeight());
                }
            }
        };

        light.setSize(200, 200);
        defaultColor = light.getBackground();

        add(light);

        DragListener listener = new DragListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    public void reset() {
        light.setBackground(defaultColor);
        repaint();
    }

    public void correct() {
        light.setBackground(rightColor);
        repaint();
    }

    public void incorrect() {
        light.setBackground(wrongColor);
        repaint();
    }

    public void answer() {
        light.setBackground(answeringColor);
        repaint();
    }

    class DragListener extends MouseInputAdapter
    {
        Point location;
        MouseEvent pressed;

        public void mousePressed(MouseEvent me)
        {
            pressed = me;

            //wheel click
            if (me.getButton() == 2) {
                game.receiveMessage(player.getName(), "+");
            }

            //right click
            if (me.getButton() == 3) {
                game.toggleScore(player);
            }
        }

        public void mouseDragged(MouseEvent me)
        {
            Component component = me.getComponent();
            location = component.getLocation(location);
            int x = location.x - pressed.getX() + me.getX();
            int y = location.y - pressed.getY() + me.getY();
            component.setLocation(x, y);
        }
    }
}
