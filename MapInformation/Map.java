package MapInformation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;


public class Map {
    private final static String googleStaticMapKey = "key=AIzaSyCe2QTqtlP97_PkLoGSn03IZ7Nc4XviWXI";

    private String center;
    private Integer zoom = 10; // Roadmap zoom

    private int height = 720;
    private int width = 640;

    private String originCity;
    private String destinationCity;

    private LinkedList<Route> routesList;

    private BufferedImage mapImage;

    /*
     * Constructors
     */

    public Map(String originCity, String destinationCity, LinkedList<Route> routesList) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.routesList = routesList;
        // Defaults
        center = originCity;
        height = 640;
        width = 720;
    }

    /*
     * Getters and Setters
     */
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    /*
     * Get BufferedImage
     */

    public BufferedImage getMapImage() {
        return mapImage;
    }

    public void setMapImage(URL url) {
        try {
            mapImage = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }

    public void setMapImage(Integer zoom) {
        try {
            mapImage = ImageIO.read(new URL(obtainUrlStaticMap(zoom)));
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }

    /*
     * Get Map Image (set as jpeg)
     */
    public void downloadMap(URL urlMap) {
        try {
            mapImage = ImageIO.read(urlMap);
            File file = new File("downloaded.jpg");
            ImageIO.write(mapImage, "jpg", file);
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }

    /*
     * Obtain urls for maps (pverloading)
     */
    public String obtainUrlStaticMap(String center, Integer zoom) {
        String URL = "https://maps.googleapis.com/maps/api/staticmap?"
                + googleStaticMapKey + "&";
        URL += "center=";
        URL += center;

        URL += "&zoom=";
        URL += zoom.toString(); // zoom city

        URL += "&size=";
        URL += width;
        URL += "x";
        URL += height;

        /*
         * Reseting image
         */
        try {
            setMapImage(new URL(URL));
        } catch(Exception e) {
            System.out.println(e);
        }
        return URL;
    }

    public String obtainUrlStaticMap(LinkedList<Route> routesList) {
        String URL = obtainUrlStaticMap(center, zoom);
        /*
         * Add all routes to map
         */
        String color = "red";
        for (Route route : routesList) {
            String polylineEncoding = route.getPolylineEncoding();

            if ((int) Math.round(route.getBreezer()) >= 70) {
                color = "green";
            } else if ((int) Math.round(route.getBreezer()) < 70
                    && (int) Math.round(route.getBreezer()) >= 40) {
                color = "orange";
            } else {
                color = "red";
            }

            if (route.isBest()) {
                color = "green";
            }
            URL += "&path=color:" + color + "|weight:3|";
            URL += "enc:";
            URL += polylineEncoding;
        }

        /*
         * Reseting image
         */
        try {
            setMapImage(new URL(URL));
        } catch(Exception e) {
            System.out.println(e);
        }
        return URL;
    }

    public String obtainUrlStaticMap(Integer zoom) {
        String URL = obtainUrlStaticMap(center, zoom);
        /*
         * Add all routes to map
         */
        String color = "red";
        for (Route route : routesList) {
            String polylineEncoding = route.getPolylineEncoding();

            if ((int) Math.round(route.getBreezer()) >= 70) {
                color = "orange";
            } else if ((int) Math.round(route.getBreezer()) < 70
                    && (int) Math.round(route.getBreezer()) >= 40) {
                color = "red";
            } else {
                color = "black";
            }

            if (route.isBest()) {
                color = "green";
            }
            URL += "&path=color:" + color + "|weight:3|";
            URL += "enc:";
            URL += polylineEncoding;
        }
        /*
         * Reseting image
         */
        try {
            setMapImage(new URL(URL));
        } catch(Exception e) {
            System.out.println(e);
        }
        return URL;
    }

    public String obtainUrlStaticMap(Point center) {
        String URL = "https://maps.googleapis.com/maps/api/staticmap?"
                + googleStaticMapKey + "&";
        URL += "center=";
        URL += center.getLat() + "," + center.getLng();

        URL += "&zoom=";
        URL += zoom.toString(); // zoom city

        URL += "&size=";
        URL += width;
        URL += "x";
        URL += height;
        /*
         * Add all routes to map
         */
        String color = "red";
        for (Route route : routesList) {
            String polylineEncoding = route.getPolylineEncoding();

            if ((int) Math.round(route.getBreezer()) >= 70) {
                color = "orange";
            } else if ((int) Math.round(route.getBreezer()) < 70
                    && (int) Math.round(route.getBreezer()) >= 40) {
                color = "red";
            } else {
                color = "black";
            }

            if (route.isBest()) {
                color = "green";
            }
            URL += "&path=color:" + color + "|weight:3|";
            URL += "enc:";
            URL += polylineEncoding;
        }
        /*
         * Reseting image
         */
        try {
            setMapImage(new URL(URL));
        } catch(Exception e) {
            System.out.println(e);
        }
        return URL;
    }
}
