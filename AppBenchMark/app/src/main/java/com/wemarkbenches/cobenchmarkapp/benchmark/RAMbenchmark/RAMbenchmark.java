package com.wemarkbenches.cobenchmarkapp.benchmark.RAMbenchmark;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;
import java.util.Random;


public class RAMbenchmark implements IBenchmark {

    private byte[] _buffer;
    public  byte[] _target;

    public RAMbenchmark() {

        final int size = 50*1024*1024;
        final Random random = new Random(0);
    }


    public void initialize(Object... param) {

    }

    @Override
    public void run(Object... param) {

    }

    @Override
    public void run() {

        for (long  i = 0; i < 100;i++) {

            _target = new byte[_buffer.length];
            System.arraycopy(_buffer,0,_target,0,_buffer.length);
        }
    }

    @Override
    public void clean() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void warmUp() {

    }

    @Override
    public String getResult() {
        return null;
    }
}
