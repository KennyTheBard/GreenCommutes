package MapInformation;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {
    /*
     * Get response from http request (return as string)
     */
    public static String getResponse(URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        } catch (Exception e) {
            //System.out.println("Exception " + e);
            return "";
        }
    }

    /*
     * Get list of routes and points
     */
    public static LinkedList<Route> getRoutes(String origin_city,
                                              String destination_city) {

        LinkedList<Route> routesList = new LinkedList<>();
        try {
            URL url = new URL(URLgetter.obtainURLdirections(origin_city,
                    destination_city));


            // JSON parsing
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(getResponse(url));

            JSONArray routes = (JSONArray) jsonObject.get("routes");

            // Iterate through all the routes
            Iterator iterator = routes.iterator();
            while (iterator.hasNext()) {

                // Add a route as a list
                routesList.add(new Route());
                JSONObject routeElement = (JSONObject) iterator.next();

                JSONArray legs = (JSONArray) jsonParser
                        .parse(routeElement.get("legs").toString());

                // Get polylineEncoding
                JSONObject polyline = (JSONObject) jsonParser.parse(routeElement.get("overview_polyline").toString());
                routesList.getLast().setPolylineEncoding(polyline.get("points").toString());

                JSONObject legs0 = (JSONObject) jsonParser
                        .parse(legs.get(0).toString());

                JSONArray stepsArray = (JSONArray) jsonParser
                        .parse(legs0.get("steps").toString());
                Iterator stepsIterator = stepsArray.iterator();

                // lastPoint used
                JSONObject lastPoint = new JSONObject();
                while (stepsIterator.hasNext()) {

                    JSONObject points = (JSONObject) jsonParser
                            .parse(stepsIterator.next().toString());

                    // Get one point

                    JSONObject duration = (JSONObject) jsonParser
                            .parse(points.get("duration").toString());
                    String time = duration.get("text").toString();
                    routesList.getLast().addTime(time);

                    JSONObject distance = (JSONObject) jsonParser
                            .parse(points.get("distance").toString());
                    String dist = distance.get("text").toString();
                    routesList.getLast().addDistance(dist);

                    JSONObject startLocationPoint = (JSONObject) jsonParser
                            .parse(points.get("start_location").toString());
                    String lat = startLocationPoint.get("lat").toString();
                    String lng = startLocationPoint.get("lng").toString();
                    routesList.getLast().addPoint(new Point(lat, lng));
                    lastPoint = (JSONObject) jsonParser
                            .parse(points.get("end_location").toString());
                }
                /*
                 * Adding the last element
                 */
                String lat = lastPoint.get("lat").toString();
                String lng = lastPoint.get("lng").toString();
                routesList.getLast().addPoint(new Point(lat, lng));
            }
        } catch (Exception e) {
            //System.out.println("Exception " + e);
            return null;
        }
        return routesList;
    }

    public static double getAQI(LinkedList<Point> pointsList) {

        int num = (int) Math.ceil(pointsList.size() / 5);

        int AQI = 0;
        try {
            for (int i = 0; i < pointsList.size(); i += num) {
                URL url = new URL(
                        URLgetter.obtainURLbreeze(pointsList.get(i).getLat(),
                                pointsList.get(i).getLng()));

                StringTokenizer stringTokenizer = new StringTokenizer(
                        getResponse(url), "\": {}");
                stringTokenizer.nextToken();
                AQI += Integer.parseInt(stringTokenizer.nextToken());
            }
        } catch (Exception e) {
            //System.out.println("Exception " + e);
        }

        AQI /= pointsList.size();
        return AQI;
    }

    /*
     *  Priority queue
     */
    public static Comparator<Route> idComparator = new Comparator<Route>(){
        @Override
        public int compare(Route c1, Route c2) {
            double a = (100-c1.getBreezer()+c1.getDistance())/2;
            double b = (100-c2.getBreezer()+c2.getDistance())/2;

            return (int) (a-b);
        }
    };

    public static LinkedList<Route> order (LinkedList<Route> routes){
        LinkedList<Route> threeRoutes = new LinkedList<>();
        PriorityQueue<Route> queue = new PriorityQueue<Route>(idComparator);
        for (int i = 0; i <routes.size() ; i++) {
            queue.add(routes.get(i));
        }
        Iterator i = queue.iterator();
        int number = 0;
        while (i.hasNext() && number < 3) {
            Route r = ((Route) i.next());
            threeRoutes.add(r);
            number++;
        }

        return threeRoutes;
    }
}
