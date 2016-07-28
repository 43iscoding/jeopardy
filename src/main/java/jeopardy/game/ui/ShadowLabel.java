package jeopardy.game.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by XLIII on 2015-12-22.
 */

public class ShadowLabel extends JLabel {

    private String text;

    private Font f;

    private Color mainColor;
    private Color shadowColor;

    public ShadowLabel() {
        super();
    }

    public ShadowLabel(String text, Font font, Color mainColor, Color shadowColor) {
        super();
        this.text = text;
        this.f = font;
        this.mainColor = mainColor;
        this.shadowColor = shadowColor;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        // ////////////////////////////////////////////////////////////////
        // antialiasing
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // ////////////////////////////////////////////////////////////////

        /**
         * draw text
         */

        g2D.setFont(f);
        g2D.setColor(mainColor);
        g2D.drawString(this.text, 1, 11);
        g2D.setColor(shadowColor);
        g2D.drawString(this.text, 0, 10);

        g2D.dispose();
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    /**
     * Default UID
     */
    private static final long serialVersionUID = 1L;

}

