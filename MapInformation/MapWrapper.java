package MapInformation;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class MapWrapper {

    private static MapWrapper instance = new MapWrapper();

    private MapWrapper() {
    };

    public static MapWrapper getMapWrapper() {
        return instance;
    }

    private LinkedList<Route> routesList;
    private Map map;
    private Point center;

    public void initMapWrapper(String originCity, String destinationCity,
                               LinkedList<Route> routesList) {
        map = new Map(originCity, destinationCity, routesList);
        this.routesList = routesList;
    }

    public void createMap() {
        map.obtainUrlStaticMap(routesList);
        center = new Point(
                routesList.getLast().getPointList().get(0).getLat(),
                routesList.getLast().getPointList().get(0).getLng());

    }

    public void zoomIn() {
        map.setZoom(map.getZoom() + 1);
        map.obtainUrlStaticMap(map.getZoom());
    }

    public void zoomOut() {
        map.setZoom(map.getZoom() - 1);
        map.obtainUrlStaticMap(map.getZoom());
    }

    public BufferedImage getMap() {
        return map.getMapImage();
    }

    public void moveRight() {
        center.setLng(String.valueOf(Double.parseDouble(center.getLng()) + 0.02));
        map.obtainUrlStaticMap(center);
    }

    public void moveLeft() {
        center.setLng(String.valueOf(Double.parseDouble(center.getLng()) - 0.02));
        map.obtainUrlStaticMap(center);
    }

    public void moveUp() {
        center.setLat(String.valueOf(Double.parseDouble(center.getLat()) + 0.02));
        map.obtainUrlStaticMap(center);
    }

    public void moveDown() {
        center.setLat(String.valueOf(Double.parseDouble(center.getLat()) - 0.02));
        map.obtainUrlStaticMap(center);
    }
}
