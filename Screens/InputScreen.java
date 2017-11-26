package Screens;

import Autentification.UserManagement;
import Control.GlobalSpecs;
import Control.Program;
import GraphicElements.*;
import Utils.EnhancedDrawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class InputScreen extends AbstractScreen {

    protected InputBox plecare, sosire;
    protected TextBox error;

    public static AbstractScreen getInstance() {
        if (instance == null) {
            instance = new InputScreen();
        }
        if (instance.getClass() != new InputScreen().getClass()) {
            instance = new InputScreen();
        }
        return instance;
    }

    @Override
    public void tick() {
        super.tick();
        if (objects.size() == 0) {
            int width = GlobalSpecs.getInstance().getWidth();
            int height = GlobalSpecs.getInstance().getHeight();
            objects.add(new ColorBox(0, 0, GlobalSpecs.getInstance().getWidth(),
                    GlobalSpecs.getInstance().getHeight()/6,
                    EnhancedDrawer.parseColorRGB("#1D2951")));

            objects.add(new TextBox("Loged as " + UserManagement.getCurrUser()
                    , Color.white, new Rectangle(0, 0,
                    GlobalSpecs.getInstance().getWidth(), GlobalSpecs.getInstance().getHeight()/6),
                    new Font("SANS_SERIF", Font.BOLD|Font.ITALIC, 32)));

            plecare = new InputBox("Departure point", width/6, height/8 * 3,
                    width * 4 / 6, height / 7);
            plecare.setSelected(true);
            objects.add(plecare);

            sosire = new InputBox("Arrival point",width/6, height/8 * 5 - 40,
                    width * 4 / 6, height / 7, true);
            objects.add(sosire);

            Button button = new Button("Find Rountes", width/6 - 90, height/8 * 7 - 80,
                    width / 2 - width / 6 + 60 , height / 7);
            button.setScreen(Program.SCREEN.MapScreen);
            objects.add(button);

            button = new Button("Log Out", width / 2, height/8 * 7 - 80,
                    width * 5 / 6 - width / 2 + 90, height / 7);
            button.setScreen(Program.SCREEN.LogInScreen);
            objects.add(button);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (error != null) {
            error.render(g);
        }
    }

    @Override
    public void passKeyEvent(KeyEvent e) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) instanceof Selectable) {
                if (((Selectable) objects.get(i)).getSelected()) {
                    if (changeSelected(e)) {
                        ((Selectable) objects.get(i)).setSelected(false);
                        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                            selectNext(i);
                        } else {
                            selectPrev(i);
                        }
                        return;
                    } else {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if (objects.get(i) instanceof Button) {
                                if (((Button) objects.get(i)).getButtonName().matches("Find Rountes")) {
                                    if (checkRoutesConfig()) {
                                        if (/**VERIFICARE DACA EXISTA RUTA/LOCATIILE*/true) {
                                            GlobalSpecs.getInstance().setRoute(plecare.getText(), sosire.getText());
                                            ((Button) objects.get(i)).keyPressed(e);
                                        } else {
                                            int width = GlobalSpecs.getInstance().getWidth();
                                            int height = GlobalSpecs.getInstance().getHeight();
                                            error = new TextBox("Wrong username/password", Color.red,
                                                    new Rectangle(width/4, height/8 + 65, width/2, height/6),
                                                    new Font("SANS_SERIF", Font.BOLD|Font.ITALIC, 32));
                                        }

                                    }

                                } else {
                                    UserManagement.logOut();
                                    ((Button) objects.get(i)).keyPressed(e);
                                }
                            }
                        } else {
                            ((Selectable) objects.get(i)).keyPressed(e);
                        }
                        return;
                    }
                }
            }
        }
    }

    private boolean changeSelected(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_UP) {
            return true;
        } else {
            return false;
        }
    }

    private void selectNext(int i) {
        for(int j = i + 1 ; j < objects.size(); j++) {
            if (objects.get(j) instanceof Selectable) {
                ((Selectable) objects.get(j)).setSelected(true);
                return;
            }
        }
        for(int j = 0 ; j < objects.size(); j++) {
            if (objects.get(j) instanceof Selectable) {
                ((Selectable) objects.get(j)).setSelected(true);
                return;
            }
        }
    }

    private void selectPrev(int i) {
        for(int j = i - 1 ; j >= 0; j--) {
            if (objects.get(j) instanceof Selectable) {
                ((Selectable) objects.get(j)).setSelected(true);
                return;
            }
        }
        for(int j = objects.size() - 1 ; j >= 0; j--) {
            if (objects.get(j) instanceof Selectable) {
                ((Selectable) objects.get(j)).setSelected(true);
                return;
            }
        }
    }

    private boolean checkRoutesConfig() {
        boolean check = true;
        if (plecare.getText().length() <= 0 || sosire.getText().length() <= 0) {
            check = false;
        }
        if (!check) {
            int width = GlobalSpecs.getInstance().getWidth();
            int height = GlobalSpecs.getInstance().getHeight();
            error = new TextBox("Complete the configuration", Color.red,
                    new Rectangle(width/4, height/8 + 65, width/2, height/6),
                    new Font("SANS_SERIF", Font.BOLD|Font.ITALIC, 32));
        }
        return check;
    }
}
