package com.wemarkbenches.cobenchmarkapp.benchmark.SSDbenchmark;

import android.content.Context;
import android.os.Environment;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;

import java.io.IOException;

public class HDDWriteSpeed implements IBenchmark {
    @Override
    public void initialize(Object... params) {
    }

    @Override
    public void warmUp() {
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options) {
        FileWriter writer = new FileWriter();
        // either "fs" - fixed size, or "fb" - fixed buffer
        String option = (String) options[0];
        // true/false whether the written files should be deleted at the end
        Boolean clean = (Boolean) options[1];

        String path = Environment.getDataDirectory().toString();
        String prefix = "/write";
        String suffix = ".dat";

        int startIndex = 0;
        int endIndex = 8;
        long fileSize = 256*1024*1024; // 256 MB
        int bufferSize = 4*1024; // 4 KB

        try {
            if (option.equals("fs"))
                writer.streamWriteFixedSize(path + prefix, suffix, startIndex,
                        endIndex, fileSize, clean);
            else if (option.equals("fb"))
                writer.streamWriteFixedBuffer(prefix, suffix, startIndex,
                        endIndex, bufferSize, clean);
            else
                throw new IllegalArgumentException("Argument "
                        + options[0].toString() + " is undefined");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
    }

    @Override
    public void cancel() {

    }

    @Override
    public String getResult() {
        return null;
    }

}
