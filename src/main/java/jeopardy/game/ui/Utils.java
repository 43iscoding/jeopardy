package jeopardy.game.ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by XLIII on 2015-12-13.
 */
public class Utils {

    private static final String fontPath = "src/main/resources/fonts/swiss911.ttf";
    private static boolean fontInitialized;

    public static Font getJeopardyFont(int style, float size) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(style, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        if (!fontInitialized) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            if (font != null) {
                ge.registerFont(font);
                fontInitialized = true;
            } else {
                System.out.println("Could not create font: swiss911");
            }
        }

        return font;
    }

    public static String wrapAndCenter(String text) {
        return "<html><div style=\"text-align: center;\">" + text + "</html>";
    }

    public static String unwrap(String text) {
        return text.replace("<html><div style=\"text-align: center;\">", "").replace("</html>", "");
    }
}
