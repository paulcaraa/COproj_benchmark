package com.wemarkbenches.cobenchmarkapp.timing;

public class TimeUnits {

    static public long miliToNano(long n){
        return n*1000000;
    }

    static public long nanoToMili(long n){
        return n/1000000;
    }
}
