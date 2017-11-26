package MapInformation;
public class URLgetter {

    private final static String googleKey = "&key=AIzaSyDSiHZkLgXGYtAFTfMK8xkAACiPD4TmSx8";
    private final static String googleDirectionKey = "&key=AIzaSyAN5bYqj0Z7HaQB_wHGfaecMNS0Z1mZnRk";
    //private final static String breezoKey = "&key=df9947986f5d43a494602973ce129612";
    private final static String breezoKey = "&key=40f452c16d5e48e6b6b657e0487a57e2 ";

    public static String obtainURLbreeze(String lat, String lon){

        String URL = "https://api.breezometer.com/baqi/?lat=";
        URL += lat;
        URL += "&lon=";
        URL += lon;
        URL += "&fields=breezometer_aqi";
        URL += breezoKey;
        return URL;
    }

    public static String obtainURLdistance(String fromCity, String toCity){
        String URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
        URL += fromCity;
        URL += "&destinations=";
        URL += toCity;
        URL += googleKey;
        return URL;
    }

    public static String obtainURLdirections(String fromCity, String toCity){
        String URL = "https://maps.googleapis.com/maps/api/directions/json?origin=";
        URL += fromCity;
        URL += "&destination=";
        URL += toCity;
        URL += "&alternatives=true";
        URL += googleDirectionKey;
        return URL;
    }

    public static String obtainURLlocation(String address){
        String URL ="https://maps.googleapis.com/maps/api/geocode/json?address=";
        URL += address;
        URL += googleKey;
        return URL;
    }

}
