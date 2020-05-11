package com.wemarkbenches.cobenchmarkapp.benchmark.CPUbenchmark;

import android.content.Context;

import com.wemarkbenches.cobenchmarkapp.timing.*;

public class CPUBenchmark {

    static private Context context;

    public CPUBenchmark(Context context) {
        this.context=context;
    }

    public static String run_bench(){

        ITimer timer = new Timer();
        IBenchmarkCPU bm = new Benchmark(context);

        long intScore, floatScore, cryptoScore, finalScore;

        bm.init("25Kint.txt","integer");
        bm.warmUp("integer");
        timer.start();
        bm.run("integer");
        intScore = TimeUnits.nanoToMili(timer.pause()); //convert ns to ms
        bm.clean("integer");

        bm.init("25Kfloat.txt","float");
        bm.warmUp("float");
        timer.resume();
        bm.run("float");
        floatScore = TimeUnits.nanoToMili(timer.pause()) - intScore; //convert ns to ms
        bm.clean("float");

        bm.init("25Kcrypto.txt","crypto");
        bm.warmUp("crypto");
        timer.resume();
        bm.run("crypto");
        finalScore = TimeUnits.nanoToMili(timer.stop()); //convert ns to ms
        cryptoScore = finalScore - floatScore - intScore;
        bm.clean("crypto");

        String score = "Score: " + (finalScore/1000)*2 + " Time elapsed: " + finalScore;
        System.out.println(score);
        return score;
    }
}
