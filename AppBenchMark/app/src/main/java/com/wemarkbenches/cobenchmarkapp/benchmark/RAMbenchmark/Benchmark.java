package com.wemarkbenches.cobenchmarkapp.benchmark.RAMbenchmark;
import android.content.Context;
import android.util.Log;
import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

public class Benchmark {

    long fileSize = 1L * 1024 * 1024 * 1024;
    int bufferSize = 4 * 1024;

    private double finalScore;

    public void run(Context context)
    {
        IBenchmark rambenchmark = new RAMbenchmark(context);
        rambenchmark.run(fileSize, bufferSize);
        String result = rambenchmark.getResult();
        finalScore = Double.valueOf(result);
        Log.i("Final score: ", finalScore + "");
        rambenchmark.clean();
    }
}
