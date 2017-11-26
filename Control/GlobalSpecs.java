package Control;

public class GlobalSpecs {
    /**
     *  Clasa globala cu rol intrimiterea si primirea
     *  parametrilor din clase de nivel diferit (de
     *  exemplu de la main pana la butoane).
     */

    protected static GlobalSpecs instance;

    private static int width, height;
    private static Program.SCREEN screen;

    private static String originCity, destinationCity;


    protected GlobalSpecs() {

    }

    public static GlobalSpecs getInstance() {
        if (instance == null) {
            instance = new GlobalSpecs();
            screen = Program.SCREEN.MapScreen;
        }
        return instance;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void setRoute(String originCity, String destinationCity) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
    }

    public static String getOriginCity() {
        return originCity;
    }

    public static String getDestinationCity() {
        return destinationCity;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static Program.SCREEN getScreen() {
        return screen;
    }

    public static void setScreen(Program.SCREEN screen) {
        GlobalSpecs.screen = screen;
    }
}