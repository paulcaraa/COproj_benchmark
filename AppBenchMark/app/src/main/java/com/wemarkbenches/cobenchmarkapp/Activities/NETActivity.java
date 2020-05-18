package com.wemarkbenches.cobenchmarkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
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
import com.wemarkbenches.cobenchmarkapp.benchmark.NETWORKBenchmark.NETWORKBenchmark;
import com.wemarkbenches.cobenchmarkapp.main.MainActivity;
import com.wemarkbenches.cobenchmarkapp.timing.Timer;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class NETActivity extends AppCompatActivity {

    CircularProgressButton circularProgressButton;
    private Button button;
    private ImageView image, cancelBtn;
    private CardView result;
    Timer time = new Timer();
    private double time_result = 0;
    NETWORKBenchmark bm = new NETWORKBenchmark();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_e_t);

        result = (CardView) findViewById(R.id.result);
        result.setVisibility(View.INVISIBLE);


        cancelBtn = (ImageView) findViewById(R.id.btn_cancel);
        cancelBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btn_cancel) {
                    Toast.makeText(NETActivity.this, "Benchmark cancelled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NETActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        button = (Button) findViewById(R.id.btnback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(v.getId() == R.id.btnback) {
                    Intent intent = new Intent(NETActivity.this, MainActivity.class);
                    startActivity(intent);
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
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
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
                AsyncTask<String, String, String> demo = new AsyncTask<String, String, String>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    protected String doInBackground(String... params) {

                        bm.initialize();
                        time.start();
                        bm.run();
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
                            MyScore.setText(bm.getResult());
                            TextView MyTime = (TextView)findViewById(R.id.result_time);
                            MyTime.setText(newStr);
                            TextView MyPhone = (TextView)findViewById(R.id.result_component);
                            MyPhone.setText(deviceName);

                            result.setVisibility(View.VISIBLE);
                            Toast.makeText(NETActivity.this, "Benchmark done", Toast.LENGTH_SHORT).show();
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