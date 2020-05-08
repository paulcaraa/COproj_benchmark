package com.wemarkbenches.cobenchmarkapp.benchmark.CPUbenchmark;

import java.util.ArrayList;

public interface IBenchmarkCPU {
    ArrayList<Integer> arrInt = new ArrayList<>();
    ArrayList<Float> arrFloat = new ArrayList<>();
    void init(Object ... params);
    void clean(String input);
    void cancel();
    void run(Object ... params);
    void warmUp(Object ... params);
}
