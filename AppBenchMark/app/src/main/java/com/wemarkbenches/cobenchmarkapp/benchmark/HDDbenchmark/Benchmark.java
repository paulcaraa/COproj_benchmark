package com.wemarkbenches.cobenchmarkapp.benchmark.HDDbenchmark;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

public class Benchmark {
    public static void run(){
        IBenchmark myHDDWriteBenchmarkfs = new HDDWriteSpeed();
        IBenchmark myHDDWriteBenchmarkfb= new HDDWriteSpeed();

        IBenchmark myHDDRandomBenchmark =new HDDRandomAccess();

        myHDDWriteBenchmarkfs.run("fs", true);
        String fsResult = myHDDWriteBenchmarkfs.getResult();

        myHDDWriteBenchmarkfb.run("fb", true);
        String fbResult = myHDDWriteBenchmarkfb.getResult();





    }
}
