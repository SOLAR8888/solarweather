package com.solarapp.solarweather.api;

import java.util.Random;

/**
 * Created by vereskun on 30.01.2018.
 */

public class ApiKeys {

    private static String[] keys = {
            "2eeb6f1df5bea97eade32d1213b497e4"
    };
    public static String getKey(){
        Random random = new Random();
        int index = random.nextInt(keys.length);
        String key = keys[index];
        return  key;
    }
}
