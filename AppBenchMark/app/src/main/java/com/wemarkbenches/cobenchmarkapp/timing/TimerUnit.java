package com.wemarkbenches.cobenchmarkapp.timing;

public enum TimerUnit {
    SEC,
    MS,
    MICROS;



    public static void  convertTime(String string) {
        StringBuilder s = new StringBuilder();

        switch(string) {
            case "SEC":
                s.append(string);
            case "MS":
                s.append(string);
            case "MICROS":
                s.append(string);
        }
    }
}