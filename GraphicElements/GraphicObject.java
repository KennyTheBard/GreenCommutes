package GraphicElements;

import java.awt.*;

public interface GraphicObject {
    /**
     *  Interfata pentru toate obiectele ce vor fi
     *  cuprinse intr-o fereastra si vor fi rendate
     *  si actionate de catre aceasta.
     */

    void render(Graphics g);

    void tick();

}
