package Control;

import Screens.AbstractScreen;
import Screens.InputScreen;
import Screens.LoadingScreen;
import Screens.LogInScreen;
import Screens.MapScreen;
import Screens.SignUpScreen;

public class ScreenSelector {
    /**
     *  Factory pentru ferestre/laturi ale aplicatiei.
     */

    private static ScreenSelector instance;

    private ScreenSelector() {

    }

    public static ScreenSelector getInstance() {
        if (instance == null) {
            instance = new ScreenSelector();
        }
        return instance;
    }

    public AbstractScreen getScreen() {
        switch (GlobalSpecs.getScreen()) {
            case LogInScreen:
                return LogInScreen.getInstance();
            case SignUpScreen:
                return SignUpScreen.getInstance();
            case InputScreen:
                return InputScreen.getInstance();
            case MapScreen:
                return MapScreen.getInstance();
            default:        /** Loading Screen case */
                return LoadingScreen.getInstance();
        }
    }

}
