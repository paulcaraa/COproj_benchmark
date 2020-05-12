package com.wemarkbenches.cobenchmarkapp.benchmark.HDDbenchmark;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

public class Benchmark {
    public static void run(){
        int bufferSize;

        IBenchmark myHDDWriteBenchmark = new HDDWriteSpeed();

        IBenchmark myHDDRandomBenchmark =new HDDRandomAccess();


        myHDDWriteBenchmark.run("fs", true);
        String fswriteResult = myHDDWriteBenchmark.getResult();

        myHDDWriteBenchmark.run("fb", true);
        String fbwriteResult = myHDDWriteBenchmark.getResult();


        for(int i = 0; i < 4; i++) {
            if(i == 0)
                bufferSize = 512;
            else if(i == 1)
                bufferSize = 1024 * 4;
            else if(i == 2)
                bufferSize = 1024 * 64;
            else
                bufferSize = 1024 * 1024;

            myHDDRandomBenchmark.run("r", "fs", bufferSize);
            String RandomReadfs = myHDDRandomBenchmark.getResult() + " for buffer size " + bufferSize;

            myHDDRandomBenchmark.run("r", "ft", bufferSize);
            String RandomReadft = myHDDRandomBenchmark.getResult() + " for buffer size " + bufferSize;

            myHDDRandomBenchmark.run("w", "fs", bufferSize);
            String RandomWritefs = myHDDRandomBenchmark.getResult() + " for buffer size " + bufferSize;

            myHDDRandomBenchmark.run("w", "ft", bufferSize);
            String RandomWriteft = myHDDRandomBenchmark.getResult() + " for buffer size " + bufferSize;
        }





    }
}
