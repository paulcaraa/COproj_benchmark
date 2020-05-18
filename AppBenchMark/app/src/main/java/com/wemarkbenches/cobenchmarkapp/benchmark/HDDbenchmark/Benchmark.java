package com.wemarkbenches.cobenchmarkapp.benchmark.HDDbenchmark;

import android.content.Context;
import android.util.Log;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

public class Benchmark {



    private double finalScore;

    public double getFinalScore() {
        return finalScore;
    }

    public void run(Context appContext){
        int bufferSize;

        double[] finalResult = new double[4];

        IBenchmark myHDDWriteBenchmark = new HDDWriteSpeed(appContext);
        IBenchmark myHDDRandomBenchmark = new HDDRandomAccess(appContext);


        myHDDWriteBenchmark.run("fs", true);
        String fswriteResult = myHDDWriteBenchmark.getResult();
       Log.i("writefs", fswriteResult);

        myHDDWriteBenchmark.run("fb", true);
        String fbwriteResult = myHDDWriteBenchmark.getResult();
        Log.i("writefb", fbwriteResult);

        for(int i = 0; i < 4; i++) {
            if(i == 0)
                bufferSize = 512;
            else if(i == 1)
                bufferSize = 1024 * 4;
            else if(i == 2)
                bufferSize = 1024 * 64;
            else
                bufferSize = 1024 * 1024;

            myHDDRandomBenchmark.initialize(String.valueOf(bufferSize));

            myHDDRandomBenchmark.run("r", "fs", 4*1024);
            String RandomReadfs = myHDDRandomBenchmark.getResult();
            Log.i("readRanfs", RandomReadfs);

            myHDDRandomBenchmark.run("r", "ft", 4*1024);
            String RandomReadft = myHDDRandomBenchmark.getResult();
            Log.i("readRanft", RandomReadft);

            myHDDRandomBenchmark.run("w", "fs", 4*1024);
            String RandomWritefs = myHDDRandomBenchmark.getResult();
            Log.i("writeRanfs", RandomWritefs);

            myHDDRandomBenchmark.run("w", "ft", 4*1024);
            String RandomWriteft = myHDDRandomBenchmark.getResult();
            Log.i("writeRanft", RandomWritefs);

            finalResult[i] = Double.valueOf(RandomReadfs) + Double.valueOf(RandomReadft) + Double.valueOf(RandomWritefs) + (Double.valueOf(RandomWriteft)/10);
            finalResult[i] /= 4;

        }
        //Score in average MB/s


        finalScore = Double.valueOf(fswriteResult) + Double.valueOf(fbwriteResult);

        for(int i = 0; i < 4; i++){
            finalScore += finalResult[i];
        }

        finalScore /= 6;
        Log.i("final result", finalScore + "");

    }
}
