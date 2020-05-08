package com.wemarkbenches.cobenchmarkapp.benchmark.CPUbenchmark;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Benchmark implements IBenchmarkCPU{

    private Context context;
    public Benchmark(Context context) {
        this.context=context;
    }

    private void computeSHAHash(String password)
    {
        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("Benchmark", "Error initializing SHA1");
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        StringBuffer sb = new StringBuffer();
        String hex;

        hex = Base64.encodeToString(data, 0, data.length, 0);
        sb.append(hex);
    }


    public void init(Object... params) {
        String fileName = params[0].toString();
        String option = params[1].toString();
        if(option.equals("crypto"))
            return;
        System.out.println(fileName);
        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            String line;
            while ((line = br.readLine()) != null) {
                if(option.equals("integer"))
                    arrInt.add(Integer.parseInt(line));
                else if(option.equals("float"))
                    arrFloat.add(Float.parseFloat(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void clean(String input) {
        if(input.equals("integer"))
            arrInt.clear();
        else if(input.equals("float"))
            arrFloat.clear();
    }

    public void cancel() {

    }

    public void warmUp(Object ... params){
        if((params[0].toString()).equals("integer")) {
            int n = arrInt.size();
            int res;
            for (int i = 0; i < n; i++)
                res = arrInt.get(i) * arrInt.get(0) + arrInt.get(i);
        }

        else if((params[0].toString()).equals("float")) {
            int n = arrFloat.size();
            float resFloat;
            for (int i = 0; i < n; i++)
                resFloat = arrFloat.get(i) / arrFloat.get(1) + arrFloat.get(i);
        }

        else if((params[0].toString()).equals("crypto")) {
            for (int i = 0; i < 100; i++){
                computeSHAHash("the big bad wolf");
            }
        }
    }

    public void run() {

    }

    public void run(Object... params) {

        //integer benchmark
        if((params[0].toString()).equals("integer")) {
            int n = arrInt.size();


            //run a bubblesort
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (arrInt.get(j) > arrInt.get(j + 1)) {
                        int temp = arrInt.get(j);
                        arrInt.set(j, arrInt.get(j + 1));
                        arrInt.set(j + 1, temp);
                    }
            int res;

//            run other int operations(+ *)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++){
                    if(i<j)
                        res = arrInt.get(i) * arrInt.get(j) + arrInt.get(i);
                    else
                        res = arrInt.get(i) * arrInt.get(j) + arrInt.get(j);
                }
        }

        //floating point benchmark
        else if((params[0].toString()).equals("float")) {
            int n = arrFloat.size();
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (arrFloat.get(j) > arrFloat.get(j + 1)) {
                        float temp = arrFloat.get(j);
                        arrFloat.set(j, arrFloat.get(j + 1));
                        arrFloat.set(j + 1, temp);
                    }

            float resFloat;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++){
                    if(i<j)
                        resFloat = arrFloat.get(i) * arrFloat.get(j) - arrFloat.get(j);
                    else
                        resFloat = arrFloat.get(i) / arrFloat.get(j) + arrFloat.get(i);
                }
        }

        else if((params[0].toString()).equals("crypto")) {
            for (int i = 0; i < 10000; i++){
                computeSHAHash("the big bad wolf");
            }
        }
    }



}
