package com.example.lesson1;

import java.util.Random;

class DataHandler {
    int generateNumber(int min, int max) {
        Random rnd = new Random(System.currentTimeMillis());
        return (min + rnd.nextInt(max - min + 1));
    }
}
