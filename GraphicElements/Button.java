package GraphicElements;

import Control.GlobalSpecs;
import Control.Program;
import Utils.EnhancedDrawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Button implements GraphicObject, Selectable {
    /**
     *  Implementare standard a functionalitatii unui buton.
     */

    private String buttonName;
    protected int x, y, width, height;
    protected boolean selected;
    protected Program.SCREEN screen;

    public Button(String buttonName, int x, int y, int width, int height) {
        this.buttonName = buttonName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.selected = false;
    }

    public void goToScreen() {
        GlobalSpecs.setScreen(screen);
    }

    public void setScreen(Program.SCREEN screen) {
        this.screen = screen;
    }

    @Override
    public void render(Graphics g) {
        if (selected) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.lightGray);
        }
        g.fillOval(x, y, height, height);
        g.fillOval(x + width - height, y, height, height);
        g.fillRect(x + height/2, y, width - height, height);

        g.setColor(Color.darkGray);
        EnhancedDrawer.drawCenteredString(g, buttonName,
                new Rectangle(x + height / 2, y, width - height, height),
                new Font("SANS_SERIF", Font.BOLD | Font.ITALIC, 32));
    }

    @Override
    public void tick() {

    }

    public void setSelected(boolean newSelect) {
        this.selected = newSelect;
    }


    public boolean getSelected() {
        return selected;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (selected) {
            GlobalSpecs.setScreen(screen);
        }
    }

    public String getButtonName() {
        return buttonName;
    }
}
