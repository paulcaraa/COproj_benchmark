package com.wemarkbenches.cobenchmarkapp.benchmark.SSDbenchmark;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

public class Benchmark {
    public static void run(){
        IBenchmark myHDDBenchmark = new HDDWriteSpeed();

        myHDDBenchmark.run("fs", true);
    }
}
