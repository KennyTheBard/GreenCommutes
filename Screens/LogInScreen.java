package Screens;

import Autentification.UserManagement;
import Control.GlobalSpecs;
import Control.Program;
import GraphicElements.*;
import GraphicElements.Button;

import java.awt.*;
import java.awt.event.KeyEvent;


public class LogInScreen extends AbstractScreen {

    private InputBox user, pass;
    private TextBox error;

    public static AbstractScreen getInstance() {
        if (instance == null) {
            instance = new LogInScreen();
        }
        if (instance.getClass() != new LogInScreen().getClass()) {
            instance = new LogInScreen();
        }
        return instance;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (error != null) {
            error.render(g);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(objects.size() == 0) {
            /**
             *  Add "LOG IN" message.
             */
            int width = GlobalSpecs.getInstance().getWidth();
            int height = GlobalSpecs.getInstance().getHeight();
            Font font = new Font("SANS_SERIF", Font.BOLD|Font.ITALIC, 64);
            Rectangle rect = new Rectangle(width/4, height/8, width/2, height/6);
            objects.add(new TextBox("LOG IN", Color.white, rect, font));

            /**
             *  Add Text Boxes.
             */
            user = new InputBox("Username", width/6, height/8 * 3,
                    width * 4 / 6, height / 7);
            user.setSelected(true);
            objects.add(user);

            pass = new InputBox("Password",width/6, height/8 * 5 - 40,
                    width * 4 / 6, height / 7, true);
            objects.add(pass);

            Button button = new Button("Log in", width/6, height/8 * 7 - 80,
                    width / 2 - width / 6 - 60, height / 7);
            button.setScreen(Program.SCREEN.InputScreen);
            objects.add(button);

            button = new Button("Sign Up", width / 2 + 60, height/8 * 7 - 80,
                    width * 5 / 6 - width / 2 - 60, height / 7);
            button.setScreen(Program.SCREEN.SignUpScreen);
            objects.add(button);
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
                                if (((Button) objects.get(i)).getButtonName().matches("Log in")) {
                                    if (checkLogIn()) {
                                        if (UserManagement.logIn(user.getText(), pass.getText())) {
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

    private boolean checkLogIn() {
        boolean check = true;
        if (!user.checkText() || !pass.checkText()) {
            check = false;
        }
        if (!check) {
            int width = GlobalSpecs.getInstance().getWidth();
            int height = GlobalSpecs.getInstance().getHeight();
            error = new TextBox("Username/Password too short", Color.red,
                    new Rectangle(width/4, height/8 + 65, width/2, height/6),
                    new Font("SANS_SERIF", Font.BOLD|Font.ITALIC, 32));
        }
        return check;
    }

}
