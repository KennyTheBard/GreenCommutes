package GraphicElements;

import java.awt.event.KeyEvent;

public interface Selectable {
    /**
     *  Interfata standard pentru toate obiectele
     *  grafice ce trebuie selectate pentru a fi
     *  folosite.
     */

    void setSelected(boolean newSelect);

    boolean getSelected();

    void keyPressed(KeyEvent e);

}
