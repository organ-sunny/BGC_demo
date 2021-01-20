package com.sunny.util;

public class RandomNumUtil {

    public static Integer randomNum() {
        int i;
        i = (int)((Math.random()*9+1)*100000);
        return i;
    }
}
