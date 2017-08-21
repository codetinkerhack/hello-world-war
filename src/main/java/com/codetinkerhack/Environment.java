package com.codetinkerhack;

public class Environment {

    public static String get(String name) {
        String result = System.getProperty(name);
        if (result != null)
            return result;
        else
            return System.getenv(name);
    }
}
