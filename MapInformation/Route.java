package MapInformation;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Route{
    private LinkedList<Point> pointList;
    private double distance = 0;
    private double breezer = 0;
    private double time = 0;
    private String polylineEncoding;
    private boolean isBest = false;

    public void setIsBest (boolean flag) {
        isBest = flag;
    }

    public boolean isBest() {
        return isBest;
    }

    public Route() {
        this.pointList = new LinkedList<>();
    }

    public void setPolylineEncoding(String polylineEncoding) {
        this.polylineEncoding = polylineEncoding;
    }

    public String getPolylineEncoding() {
        return polylineEncoding;
    }

    public void addPoint(Point point) {
        pointList.addLast(point);
    }

    private Double computeDistanceValue(String stringNumber) {
        StringTokenizer stringTokenizer = new StringTokenizer(stringNumber);
        double number = Double.parseDouble(stringTokenizer.nextToken());
        if (stringTokenizer.nextToken().equals("m"))
            number /= 1000;
        return number;
    }

    public void addDistance(String distance) {
        this.distance += computeDistanceValue(distance);
    }

    public void setBreezer() {
        this.breezer = Util.getAQI(pointList);
    }

    private Double computeTimeValue(String stringNumber) {
        StringTokenizer stringTokenizer = new StringTokenizer(stringNumber);
        double number = Double.parseDouble(stringTokenizer.nextToken());
        if (stringTokenizer.nextToken().contains("min"))
            number /= 60;
        return number;
    }

    public void addTime(String time) {
        this.time += computeTimeValue(time);
    }

    public LinkedList<Point> getPointList() {
        return pointList;
    }

    public void printPointList() {
        for (Point p : pointList) {
            System.out.println("Longitudine: " + p.getLng() + " Latitudine: "
                    + p.getLat());
        }
        System.out.println("Time: " + time + " h");
        System.out.println("Distance: " + distance + " km");
        System.out.println("Pollution average: " + breezer);
    }

    public double getDistance() {
        return distance;
    }

    public double getBreezer() {
        return breezer;
    }

    public double getTime() {
        return time;
    }
}
