package com.wemarkbenches.cobenchmarkapp.timing;

public interface ITimer {
    void start();
    void resume();
    long stop();
    long pause();
}
