package Screens;

import Control.GlobalSpecs;
import GraphicElements.GraphicObject;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LoadingScreen extends AbstractScreen {

    public static AbstractScreen getInstance() {
        if (instance == null) {
            instance = new LoadingScreen();
        }
        if (instance.getClass() != new LoadingScreen().getClass()) {
            instance = new LoadingScreen();
        }
        return instance;
    }

    @Override
    public void tick() {
        super.tick();
        if (objects.size() == 0) {
            objects.clear();
            addGraphicObject(new LoadingIcon());
        }
    }

    @Override
    public void passKeyEvent(KeyEvent e) {
        // nothing
    }

    private class LoadingIcon implements GraphicObject {

        private int height = GlobalSpecs.getInstance().getHeight(),
                    width = GlobalSpecs.getInstance().getWidth();

        private int angle = 0, toAdd = 30;

        @Override
        public void render(Graphics g) {
            g.setColor(Color.white);
            g.fillArc(width/2 - 100, height/2 - 100, 200, 200, angle, 300);
        }

        @Override
        public void tick() {
            angle += toAdd;
            if (angle == 60) {
                toAdd = 15;
            }
            if (angle == 140) {
                toAdd = 10;
            }
            if (angle == 220) {
                toAdd = 15;
            }
            if (angle == 300) {
                toAdd = 30;
            }
            if (angle >= 360) {
                angle = 0;
            }
        }
    }
}

