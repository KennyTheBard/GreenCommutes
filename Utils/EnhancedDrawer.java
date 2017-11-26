package Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnhancedDrawer {

    public static void drawCenteredCircle(Graphics g, int x, int y, int r, Color col) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.setColor(col);
        g.fillOval(x, y, r, r);
    }

    public static Color parseColorRGB(final String hex) {
        return new Color(Integer.parseInt(hex.substring(1), 16));
    }

    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);

        int x = (int)(rect.getX() + (rect.getWidth() - metrics.stringWidth(text)) / 2);
        int y = (int)(rect.getY() + ((rect.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());

        g.setFont(font);

        g.drawString(text, x, y);
    }

}
