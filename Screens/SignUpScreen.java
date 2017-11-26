package Screens;

import Autentification.UserManagement;
import Control.GlobalSpecs;
import Control.Program;
import GraphicElements.Button;
import GraphicElements.InputBox;
import GraphicElements.Selectable;
import GraphicElements.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SignUpScreen extends AbstractScreen {

    private InputBox user, pass, repass;
    private TextBox error;

    public static AbstractScreen getInstance() {
        if (instance == null) {
            instance = new SignUpScreen();
        }
        if (instance.getClass() != new SignUpScreen().getClass()) {
            instance = new SignUpScreen();
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
            Rectangle rect = new Rectangle(width/4, height/64 - 20, width/2, height/6);
            objects.add(new TextBox("SIGN UP", Color.white, rect, font));

            /**
             *  Add Text Boxes.
             */
            user = new InputBox("Username", width/6, height/8 * 1 + 40,
                    width * 4 / 6, height / 7);
            user.setSelected(true);
            objects.add(user);

            pass = new InputBox("Password",width/6, height/8 * 3,
                    width * 4 / 6, height / 7, true);
            objects.add(pass);

            repass = new InputBox("Confirm password",width/6, height/8 * 5 - 40,
                    width * 4 / 6, height / 7, true);
            objects.add(repass);

            Button button = new Button("Back", width/6, height/8 * 7 - 80,
                    width / 2 - width / 6 - 60, height / 7);
            button.setScreen(Program.SCREEN.LogInScreen);
            objects.add(button);

            button = new Button("Sign Up", width / 2 + 60, height/8 * 7 - 80,
                    width * 5 / 6 - width / 2 - 60, height / 7);
            button.setScreen(Program.SCREEN.InputScreen);
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
                            if (objects.get(i) instanceof GraphicElements.Button) {
                                if (((GraphicElements.Button) objects.get(i)).getButtonName().matches("Sign Up")) {
                                    if (checkPasswords()) {
                                        if (checkSignUp()) {
                                            if (UserManagement.signUp(user.getText(), pass.getText())) {
                                                ((GraphicElements.Button) objects.get(i)).keyPressed(e);
                                            } else {
                                                int width = GlobalSpecs.getInstance().getWidth();
                                                int height = GlobalSpecs.getInstance().getHeight();
                                                error = new TextBox("Username already taken", Color.red,
                                                        new Rectangle(width / 4, height / 16, width / 2, height / 6),
                                                        new Font("SANS_SERIF", Font.BOLD | Font.ITALIC, 32));
                                            }
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

    private boolean checkPasswords() {
        boolean check = pass.getText().matches(repass.getText());
        if (!check) {
            int width = GlobalSpecs.getInstance().getWidth();
            int height = GlobalSpecs.getInstance().getHeight();
            error = new TextBox("Passwords don't match", Color.red,
                    new Rectangle(width/4, height/16, width/2, height/6),
                    new Font("SANS_SERIF", Font.BOLD|Font.ITALIC, 32));
        }
        return check;
    }

    private boolean checkSignUp() {
        boolean check = true;
        if (!user.checkText() || !pass.checkText()) {
            check = false;
        }
        if (!check) {
            int width = GlobalSpecs.getInstance().getWidth();
            int height = GlobalSpecs.getInstance().getHeight();
            error = new TextBox("Username/Password at least 8 characters", Color.red,
                    new Rectangle(width/4, height/16, width/2, height/6),
                    new Font("SANS_SERIF", Font.BOLD|Font.ITALIC, 32));
        }
        return check;
    }
}
