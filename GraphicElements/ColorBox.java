package GraphicElements;

import java.awt.*;

public class ColorBox implements GraphicObject {
    /**
     *  Implementare standard pentru un dreptunghi de culoare.
     *      Foarte util, stiu.
     */

    protected int x, y, width, height;
    protected Color col;

    public ColorBox(int x, int y, int width, int height, Color col) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.col = col;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(col);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void tick() {

    }
}
