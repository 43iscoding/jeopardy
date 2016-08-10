package jeopardy.game.ui.display;

import jeopardy.game.utils.Colors;
import jeopardy.game.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/9/16
 * Time: 6:27 PM
 */
public class LogoPanel extends JPanel {

    public void init() {
        setSize(950, 500);
        setBackground(Colors.backgroundBlue);
        setLayout(new GridLayout(4, 1));

        add(new JLabel());
        try {
            BufferedImage tunaImage = ImageIO.read(new File("src/main/resources/images/tuna.png"));
            JLabel tuna = new JLabel(new ImageIcon(tunaImage.getScaledInstance(240, 110, Image.SCALE_DEFAULT)));
            add(tuna);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel("NAME THAT TUNA", JLabel.CENTER);
        label.setFont(Utils.getJeopardyFont(Font.BOLD, 80));
        label.setForeground(Colors.roundYellow);
        add(label);
        add(new JLabel());
    }
}
