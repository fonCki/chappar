package com.example.chappar10.utils;

import com.example.chappar10.model.Location;
import com.example.chappar10.model.User;

public class Distance {

    public static double GetDistance(Location location1, Location location2) {
        return GetDistance(location1.getLatitude(), location2.getLatitude(), location1.getLongitude(),  location2.getLongitude());
    }

    public static double GetDistance(User user1, User user2) {
        return GetDistance(user1.getLocation().getLatitude(), user2.getLocation().getLatitude(),user1.getLocation().getLongitude(), user2.getLocation().getLongitude());
    }

    public static double GetDistance(double lat1, double lat2, double lon1, double lon2) {
        return GetDistance(lat1, lat2, lon1, lon2, 0.0, 0.0);
    }

    public static double GetDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance) / 1000;
    }
}
