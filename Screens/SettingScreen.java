package Screens;

import java.awt.event.KeyEvent;

public class SettingScreen extends AbstractScreen {

    public static AbstractScreen getInstance() {
        if (instance == null) {
            instance = new SettingScreen();
        }
        if (instance.getClass() != new SettingScreen().getClass()) {
            instance = new SettingScreen();
        }
        return instance;
    }

    @Override
    public void passKeyEvent(KeyEvent e) {

    }
}
