package Control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    /**
     *  Keyboard input point:
     *          Trimite evenimentele de la main in jos in
     *      ierarhia claselor, pana la obiectul destinatie
     *      pentru a fi interpretat.
     */

    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        handler.passKeyEvent(e);
    }

    public void keyReleased(KeyEvent e) {
        // nothing
    }

}