package com.example.lesson1;

import java.io.Serializable;
import java.util.ArrayList;

class Parcel implements Serializable {
    int dark;
    int light;
    int humidityVisibility;
    int windVisibility;

    String city;
    String weather;
    String tempCurrent;
    String tempRange;

    String[] time;
    String[] day;
    String[] cities;
    String[] tempCities;
    String[] weather_collection;
    int[] weather_image_collection;
    int[] temperature_collection;
}
