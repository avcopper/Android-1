package com.example.lesson1;

import java.io.Serializable;
import java.util.ArrayList;

class Parcel implements Serializable {
    int dark;
    int light;
    int wind;
    int humidity;
    String city;
    String weather;
    String tempCurrent;
    String tempDay;
    String tempNight;

    String[] time;
    String[] day;
    String[] cities;
    String[] tempTime;
    String[] tempCities;
    String[] weather_collection;
    int[] weather_image_collection;
    int[] temperature_collection;
}
