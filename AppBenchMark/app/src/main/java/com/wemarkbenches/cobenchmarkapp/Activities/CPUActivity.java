package com.wemarkbenches.cobenchmarkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.device.DeviceName;
import com.wemarkbenches.cobenchmarkapp.R;
import com.wemarkbenches.cobenchmarkapp.main.MainActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import com.wemarkbenches.cobenchmarkapp.benchmark.CPUbenchmark.CPUBenchmark;
import com.wemarkbenches.cobenchmarkapp.timing.Timer;

public class CPUActivity extends AppCompatActivity {

    CircularProgressButton circularProgressButton;
    private Button button;
    private ImageView image, cancelBtn;
    private CardView result;
    static Context act_context;
    private String score = null;
    Timer time = new Timer();
    private double time_result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_p_u);

        result = (CardView) findViewById(R.id.result);
        result.setVisibility(View.INVISIBLE);

        act_context = this;

        button = (Button) findViewById(R.id.btnback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(v.getId() == R.id.btnback) {
                    Intent intent = new Intent(CPUActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        cancelBtn = (ImageView) findViewById(R.id.btn_cancel);
        cancelBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btn_cancel) {
                    Toast.makeText(CPUActivity.this, "Benchmark cancelled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CPUActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        image = (ImageView) findViewById(R.id.btnshare);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnshare) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "My CPU score: "+ score);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
            }
        });


        circularProgressButton =  (CircularProgressButton) findViewById(R.id.btnProgress);

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBtn.setVisibility(View.VISIBLE);
                @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> demo = new AsyncTask<String, String, String>() {

                    @Override
                    protected String doInBackground(String... params) {

                        CPUBenchmark bm = new CPUBenchmark(act_context);
                        time.start();
                        score = CPUBenchmark.run_bench();
                        time_result = time.stop();
                        return "Done!";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if(s.equals("Done!")) {
                            cancelBtn.setVisibility(View.INVISIBLE);
                            String deviceName = DeviceName.getDeviceName();


                            StringBuilder sb = new StringBuilder ();
                            sb.append ((int) (time_result/Math.pow(10,9)));
                            sb.append(" ");
                            sb.append ("seconds");
                            String newStr = sb.toString ();


                            TextView MyScore = (TextView)findViewById(R.id.result_score);
                            MyScore.setText(score);
                            TextView MyTime = (TextView)findViewById(R.id.result_time);
                            MyTime.setText(newStr);
                            TextView MyPhone = (TextView)findViewById(R.id.result_component);
                            MyPhone.setText(deviceName);

                            result.setVisibility(View.VISIBLE);
                            Toast.makeText(CPUActivity.this, "Benchmark done", Toast.LENGTH_SHORT).show();
                            circularProgressButton.doneLoadingAnimation(Color.parseColor("#B82E1F"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));
                        }
                    }
                };
                circularProgressButton.startAnimation();
                demo.execute();
            }
        });


    }


}
