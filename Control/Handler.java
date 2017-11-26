package Control;

import Screens.AbstractScreen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Handler{
    /**
     *      Oranizeaza rendarile si timpii astfel incat
     *  sa fie procesate numai elementele paginii curente.
     */

    public void render(Graphics g) {
        getScreen().render(g);
    }

    public void tick() {
        getScreen().tick();
    }

    private AbstractScreen getScreen() {
        return ScreenSelector.getInstance().getScreen();
    }

    public void passKeyEvent(KeyEvent e) {
        getScreen().passKeyEvent(e);
    }
}
