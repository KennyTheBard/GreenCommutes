package Control;

import Autentification.UserManagement;
import Utils.EnhancedDrawer;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class Program  extends Canvas implements Runnable {
    /**
     *  Main class
     */

    private Thread thread;
    private boolean running;
    private Handler handler;
    private static int WIDTH, HEIGHT;


    public enum SCREEN {
        LogInScreen,
        SignUpScreen,
        InputScreen,
        MapScreen,
        LoadingScreen
    }

    public Program() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        UserManagement.setUserList();

        /**
         *  TEMP DIMENSIONS
         */
        WIDTH = 720;
        HEIGHT = 640;
        GlobalSpecs.getInstance().setDimensions(WIDTH, HEIGHT);

        new Window(WIDTH, HEIGHT,"Green Commutes",this);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = 100000000;
        double delta = 0;
        render();
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 2) {
                tick();
                render();
                delta -= 2;
            }
        }
        stop();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(EnhancedDrawer.parseColorRGB("#5dad49"));
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);


        g.dispose();
        bs.show();

        Toolkit.getDefaultToolkit().sync();
    }

    private void tick() {
        handler.tick();
    }

    public static void main(String[] args) {
        new Program();
    }

}
