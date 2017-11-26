package Screens;

import Autentification.UserManagement;
import Control.GlobalSpecs;
import GraphicElements.ColorBox;
import GraphicElements.GraphicObject;
import GraphicElements.TextBox;
import MapInformation.MapWrapper;
import MapInformation.Route;
import MapInformation.Util;
import Utils.EnhancedDrawer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class MapScreen extends AbstractScreen {

    private static MapWrapper map;
    private static String breeze, dist, time;

    public static AbstractScreen getInstance() {
        if (instance == null) {
            instance = new MapScreen();
       // }
        //if (instance.getClass() != new MapScreen().getClass()) {
            instance = new MapScreen();

            GlobalSpecs.getInstance().setRoute("Bucuresti,Rosia+Montana", "Bucuresti,Valea+Rosie");

            LinkedList<Route> routesList = Util.getRoutes(GlobalSpecs.getOriginCity(),
                    GlobalSpecs.getDestinationCity());

            for (int i = 0; i < routesList.size(); i++) {
                routesList.get(i).setBreezer();
            }

            routesList = Util.order(routesList);

            // Print for test
            Route bestRoute = routesList.peek();
            breeze = bestRoute.getBreezer() + "%";
            dist = Math.round(bestRoute.getDistance()*100)/100.0 + " km";
            time =  Math.round(bestRoute.getTime()*100)/100.0 + " hours";
            bestRoute.setIsBest(true);

            /**
             * Test creating image map
             */
            map = MapWrapper.getMapWrapper();
            map.initMapWrapper(GlobalSpecs.getOriginCity(), GlobalSpecs.getDestinationCity(), routesList);
            map.createMap();
        }
        return instance;
    }

    @Override
    public void passKeyEvent(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'A' || c == 'a') {
            map.zoomOut();
        }
        if (c == 'S' || c == 's') {
            // back to standard
        }
        if (c == 'D' || c == 'd') {
            map.zoomIn();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            map.moveDown();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            map.moveUp();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            map.moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            map.moveLeft();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (objects.size() == 0) {
            int width = GlobalSpecs.getInstance().getWidth();
            int height = GlobalSpecs.getInstance().getHeight();
            objects.add(new ColorBox(0, 0, width, height / 6,
                    EnhancedDrawer.parseColorRGB("#38682c")));

            objects.add(new TextBox("Loged as " + UserManagement.getCurrUser(),
                    Color.white, new Rectangle(0, 0,
                    width, height / 12),
                    new Font("SANS_SERIF", Font.BOLD, 24)));

            objects.add(new TextBox("Commands: 'A' - zoom out  'S' - back to standard  'D' - zoom in",
                    Color.white, new Rectangle(0, 20,
                    width, height / 6),
                    new Font("SANS_SERIF", Font.BOLD, 16)));
            objects.add(new TextBox("ARROWS - move the view around",
                    Color.white, new Rectangle(0, 38,
                    width, height / 6),
                    new Font("SANS_SERIF", Font.BOLD, 16)));

            objects.add(new ColorBox(0, height * 5 / 6, width,
                    height / 6,
                    EnhancedDrawer.parseColorRGB("#38682c")));

            objects.add(new TextBox("Details:", Color.white, new Rectangle(0, height * 5 / 6,
                    width/2, height / 6),
                    new Font("SANS_SERIF", Font.BOLD | Font.ITALIC, 18)));
            objects.add(new TextBox( breeze, Color.white, new Rectangle(width/2, height * 5 / 6,
                    width/2, height / 12),
                    new Font("SANS_SERIF", Font.BOLD | Font.ITALIC, 18)));
            objects.add(new TextBox( dist, Color.white, new Rectangle(width/2, height * 5 / 6 + 20,
                    width/2, height / 12),
                    new Font("SANS_SERIF", Font.BOLD | Font.ITALIC, 18)));
            objects.add(new TextBox( time, Color.white, new Rectangle(width/2, height * 5 / 6 + 40,
                    width/2, height / 12),
                    new Font("SANS_SERIF", Font.BOLD | Font.ITALIC, 18)));

        }
    }

    private String getDetails() {
        String ret = "";
        return ret;
    }


    @Override
    public void render(Graphics g) {
        BufferedImage bi = map.getMap();
        g.drawImage(bi, 0, 0, null);

        for (GraphicObject obj : objects) {
            obj.render(g);
        }
    }

}