package com.wemarkbenches.cobenchmarkapp.benchmark;

public interface IBenchmark {
    void initialize(Object ...param);
    void run(Object ...param);
    void run();
    void clean();
    void cancel();
    void warmUp();
    String getResult();
}
