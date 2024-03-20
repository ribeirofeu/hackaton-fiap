package com.hackaton.hackton.utils;

public class Utils {

    public static Long generateProtocol(){
        long leftLimit = 1L;
        long rightLimit = 999999L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
