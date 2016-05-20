package com.brentlrayjr.androidasteroids;

import java.util.Random;

public class Utils {

    public static int generateId() {

        StringBuilder builder = new StringBuilder();



        for (int i = 0; i < 9; i++) {

            builder.append(new Random().nextInt());

        }

        return Integer.parseInt(builder.toString());
    }

}
