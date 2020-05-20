package com.wemarkbenches.cobenchmarkapp.benchmark.RAMbenchmark;
import android.content.Context;
import android.util.Log;
import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

public class Benchmark {

  //  long fileSize = 1L * 1024 * 1024 * 1024;
    long fileSize = 512 * 1024 * 1024;

    int bufferSize = 4 * 1024;

    private double finalScore = 0.0;

    public void run(Context context)
    {
        IBenchmark rambenchmark = new RAMbenchmark(context);
        rambenchmark.run(fileSize, bufferSize);
        String result = rambenchmark.getResult();
        finalScore = Double.valueOf(result);
        Log.i("Final score: ", finalScore + "");
        rambenchmark.clean();
    }

    public String getResult() {
        if(finalScore == 0.0) {
            return String.valueOf(0.0);
        }
        return String.valueOf(finalScore);
    }
}
