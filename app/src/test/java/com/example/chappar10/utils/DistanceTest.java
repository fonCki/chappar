package com.example.chappar10.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DistanceTest {
    @Test
    public void getDistanceFromFranceToGermany() {
        //arrange
        double lat1 = 48.856614;
        double lon1 = 2.3522219;
        double lat2 = 52.520008;
        double lon2 = 13.404954;
        //act
        double distance = Distance.GetDistance(lat1, lat2, lon1, lon2);
        //assert
        assertEquals(910.0, distance,50);
    }

    @Test
    public void getDistanceFromNewYorkToLondon() {
        //arrange
        double lat1 = 40.712776;
        double lon1 = -74.005974;
        double lat2 = 51.507351;
        double lon2 = -0.127758;
        //act
        double distance = Distance.GetDistance(lat1, lat2, lon1, lon2);
        //assert
        assertEquals(5570.0, distance,50);
    }

    @Test
    public void getDistanceFromNewYorkToNewYork() {
        //arrange
        double lat1 = 40.712776;
        double lon1 = -74.005974;
        double lat2 = 40.712776;
        double lon2 = -74.005974;
        //act
        double distance = Distance.GetDistance(lat1, lat2, lon1, lon2);
        //assert
        assertEquals(0.0, distance,00);
    }

    @Test
    public void getDistanceFromCopenhagenToBerlin() {
        //arrange
        double lat1 = 55.676097;
        double lon1 = 12.568337;
        double lat2 = 52.520008;
        double lon2 = 13.404954;
        //act
        double distance = Distance.GetDistance(lat1, lat2, lon1, lon2);
        //assert
        assertEquals(360.0, distance,50);
    }

    @Test
    public void getDistanceFromRomeToBarcelona() {
        //arrange
        double lat1 = 41.902782;
        double lon1 = 12.496366;
        double lat2 = 41.385064;
        double lon2 = 2.173403;
        //act
        double distance = Distance.GetDistance(lat1, lat2, lon1, lon2);
        //assert
        assertEquals(857.0, distance,50);
    }

    @Test
    public void getDistanceFromTokionToHonolulu() {
        //arrange
        double lat1 = 35.689487;
        double lon1 = 139.691706;
        double lat2 = 21.306944;
        double lon2 = -157.858333;
        //act
        double distance = Distance.GetDistance(lat1, lat2, lon1, lon2);
        //assert
        assertEquals(6187.0, distance,50);
    }

    @Test
    public void getDistanceFromNorthPoleToSouthPole() {
        //arrange
        double lat1 = 90;
        double lon1 = 0;
        double lat2 = -90;
        double lon2 = 0;
        //act
        double distance = Distance.GetDistance(lat1, lat2, lon1, lon2);
        //assert
        assertEquals(20015.0, distance,50);
    }
}
