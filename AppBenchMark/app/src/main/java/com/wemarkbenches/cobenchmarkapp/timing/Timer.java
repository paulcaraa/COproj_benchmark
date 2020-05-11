package com.wemarkbenches.cobenchmarkapp.timing;

public class Timer implements ITimer {
    private long start_time = 0;
    private long end_time = 0;
    private long elapsed_time = 0;

    public void start() {

        elapsed_time=0;
        start_time = System.nanoTime();
    }

    public long stop() {
        end_time = System.nanoTime();
        if(start_time == 0) {
            System.out.println("Timer not started");
            return -1;
        }

        return (end_time - start_time) + elapsed_time;
    }

    public void resume() {
        start_time  = System.nanoTime();
        if(start_time == 0) {
            System.out.println("Timer not prev. started");
        }

    }

    public long pause() {
        end_time = System.nanoTime();
        if(start_time == 0) {
            System.out.println("Timer not started");
            return 0;
        }
        elapsed_time += (end_time - start_time);
        return elapsed_time;
    }
}
