package com.wemarkbenches.cobenchmarkapp.benchmark.NETWORKBenchmark;

import com.wemarkbenches.cobenchmarkapp.benchmark.IBenchmark;
import com.wemarkbenches.cobenchmarkapp.timing.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;
import java.sql.Time;
import java.util.ArrayList;


public class NETWORKBenchmark implements  IBenchmark{

    private static final String FILE_ADDRESS = "ftp://speedtest:speedtest@ftp.otenet.gr/test1Gb.db";
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 512;
    private static final int BUFFER_SIZE = 1024*1024*16;

    private long size = 1024 * 1024 * 64; // how much to download;
    private double result = 0; // MB/SEC
    private  String extra;
    private volatile boolean shouldTestRun;

    public void initialize() {
        this.result=0;
    }

    public void initialize(Object ...size) {

        for (Object o: size)
        this.result+=(Long)o;
    }

    public void warmUp() {
        throw new UnsupportedOperationException();
    }

    public void run(Object ... params) {
        throw new UnsupportedOperationException();
    }

    public void run() {
        this.compute();
    }

    public void compute () {
        this.shouldTestRun = true;
        System.out.println("Benchmark started");
        int bufferSize = Math.min(BUFFER_SIZE,(int)size);
        System.out.println("" + bufferSize);

        try {
            URLConnection connection = new URL(FILE_ADDRESS).openConnection();
            connection.setUseCaches(false);
            DataInput stream = new DataInputStream(connection.getInputStream());
            byte[] buffer = new byte[bufferSize];
            Timer timer = new Timer();
            ArrayList<Double> measurements = new ArrayList<>((int) this.size / bufferSize);
            boolean eof = false;
            int totalDownload = 0;

            while (totalDownload < size && this.shouldTestRun){
                timer.start();
                try {
                    stream.readFully(buffer);
                }catch (EOFException e) {
                    eof = true;
                }
                long measure = timer.stop();
                if (eof) {
                    break;
                }
                totalDownload += bufferSize;
                measurements.add((double)bufferSize/ 1024 * 1024 / (measure / 1000000000.0));

            }
            double sum = 0;
            for (double each : measurements) {
                sum += each;
            }
            this.result = sum * 4 / measurements.size();
        }catch (IOException e) {
            System.out.println(e.toString());
        }finally {
            this.shouldTestRun = false;
        }
    }

    public void stop() {
        this.shouldTestRun = false;
    }
    public void cancel() {
        this.shouldTestRun = false;
    }
    public void clean(){

    }
    public String toString() {

        return "NetworkBenchmark-" + Long.toString((long)(this.result *1000)) + " Downloaded " + size/(1024/1024) + "MB with a speed of " + String.format(java.util.Locale.US,"%.2f",result) + " MB/s";
    }
    public String getResult() {

        return this.toString();
    }
}
