package Screens;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import GraphicElements.GraphicObject;

public abstract class AbstractScreen extends Canvas {
    /**
     *  Implementarile si metodele necesare ferestrelor aplicatiei
     *  pentru a fi rulate si actionate adecvat.
     */

    /**
     *  Singleton pattern:
     */

    protected static AbstractScreen instance;

    protected AbstractScreen() {

    }

    /**
     *  Functional elements:
     */

    public void render(Graphics g) {
        for (GraphicObject obj : objects) {
            obj.render(g);
        }
    }

    public void tick() {
        for (GraphicObject obj : objects) {
            obj.tick();
        }
    }

    public abstract void passKeyEvent(KeyEvent e);

    /**
     *  Screen elements:
     */

    protected ArrayList<GraphicObject> objects = new ArrayList<>();

    protected void addGraphicObject(GraphicObject obj) {
        objects.add(obj);
    }

}
