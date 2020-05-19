package com.wemarkbenches.cobenchmarkapp.benchmark.RAMbenchmark;
import android.content.Context;

import java.io.IOException;
import java.util.Random;
import com.wemarkbenches.cobenchmarkapp.timing.Timer;
import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

/**
 * Maps a large file into RAM triggering the virtual memory mechanism. Performs
 * reads and writes to the respective file.<br>
 * The access speeds depend on the file size: if the file can fit the available
 * RAM, then we are measuring RAM speeds.<br>
 * Conversely, we are measuring the access speed of virtual memory, implying a
 * mixture of RAM and HDD access speeds (i.e., lower).
 */

public class RAMbenchmark implements IBenchmark {
    private double result = 0.0;
    private MemoryMapper core = null;
    private Context context;
    private String PATH;

    public RAMbenchmark(Context context){
        this.context = context;
        PATH = context.getFilesDir() + "/testraf";
    }

    @Override
    public void initialize(Object... param) {

    }

    @Override
    public void run(Object... param) {
        // expected example: {fileSize, bufferSize}
        Object[] params = (Object[]) param;
        long fileSize = Long.parseLong(params[0].toString()); // e.g. 1-3GB
        int bufferSize = Integer.parseInt(params[1].toString()) ;// e.g. 4KB

        try {
            core = new MemoryMapper(PATH, fileSize); // change path as needed
            byte[] buffer = new byte[bufferSize];
            Random rand = new Random();

            Timer timer = new Timer();

            // write to VM
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                rand.nextBytes(buffer);
                core.put(i,buffer);
            }
            double writeTime = (double) timer.stop() / 1000000000; /*nano to sec */
            double writeSpeed = (double)(fileSize / 1024 / 1024) / writeTime;

            result += writeSpeed;
            /*speed, with exactly 2 decimals*/
           /* result = "\nWrote " + (fileSize / 1024 / 1024L)
                    + " MB to virtual memory at " + " MB/s";
*/
            // read from VM
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                // get from memory mapper
                core.get(i, bufferSize);
            }
            double readTime = (double) timer.stop() / 1000000000;
            double readSpeed = (fileSize / 1024 / 1024) / readTime;

            result += readSpeed;

            /*speed, with exactly 2 decimals*/
        /*    result += "\nRead " + (fileSize / 1024 / 1024L)
                    + " MB from virtual memory at " + " MB/s";

         */

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (core != null)
                core.purge();
        }

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Use run(Object[]) instead");
    }

    @Override
    public void clean() {
        if (core != null)
            core.purge();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void warmUp() {

    }

    @Override
    public String getResult() {

        return String.valueOf(result);
    }
}
