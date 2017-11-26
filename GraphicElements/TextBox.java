package GraphicElements;

import Utils.EnhancedDrawer;

import java.awt.*;

public class TextBox implements GraphicObject {
    /**
     *  Spatiu de centrare a unui text.
     */

    protected String text;
    protected Color col;
    protected Rectangle rect;
    protected Font font;

    public TextBox(String text, Color col, Rectangle rect, Font font) {
        this.text = text;
        this.col = col;
        this.rect = rect;
        this.font = font;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(col);
        EnhancedDrawer.drawCenteredString(g, text, rect, font);
    }

    @Override
    public void tick() {

    }
}
