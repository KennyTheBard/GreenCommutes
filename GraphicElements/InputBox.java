package GraphicElements;

import Utils.EnhancedDrawer;

import java.awt.*;
import java.awt.event.KeyEvent;

public class InputBox implements GraphicObject, Selectable {
    /**
     *  Casuta de primit input de pana la 20 de caractere,
     *  cu optiuni de ascundere a Stringului curent.
     */

    protected int x, y, width, height;
    protected boolean selected, hide;
    protected String text, stdText;

    public InputBox(String stdText, int x, int y, int width, int height) {
        this(stdText, x, y, width, height, false);
    }

    public InputBox(String stdText, int x, int y, int width, int height, boolean hide) {
        this.stdText = stdText;
        this.x = x;
        this.y = y;
        /**
         *  Just in case..
         */
        if (width < height) {
            width += height;
            height = width - height;
            width -= height;
        }
        this.width = width;
        this.height = height;
        this.text = "";
        this.selected = false;
        this.hide = hide;
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
        String toPrint;
        if (text.length() != 0) {
            g.setColor(Color.black);
            if (hide) {
                toPrint = "";
                for (int i = 0; i < text.length(); i++) {
                    toPrint += "*";
                }
            } else {
                toPrint = text;
            }
        } else {
            g.setColor(Color.darkGray);
            toPrint = stdText;
        }
        EnhancedDrawer.drawCenteredString(g, toPrint,
                new Rectangle(x + height / 2, y, width - height, height),
                new Font("SANS_SERIF", Font.BOLD | Font.ITALIC, 32));
    }

    @Override
    public void tick() {

    }

    public void keyPressed(KeyEvent e) {
        if (selected) {
            char c = e.getKeyChar();
            if (text.length() <= 20) {
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
                        || (c >= '0' && c <= '9') || c == '_' || c == '-'
                        || c == '.' || c == '@') {
                    text += c;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            }
        }
    }

    public void setSelected(boolean newSelect) {
        this.selected = newSelect;
    }

    public boolean getSelected() {
        return selected;
    }

    public boolean checkText() {
        return text.length() >= 8;
    }

    public String getText() {
        return text;
    }

}
