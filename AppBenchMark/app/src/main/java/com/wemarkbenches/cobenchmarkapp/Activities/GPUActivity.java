package com.wemarkbenches.cobenchmarkapp.Activities;

import com.wemarkbenches.cobenchmarkapp.benchmark.GPUbenchmark.GPUBenchmark;
import com.wemarkbenches.cobenchmarkapp.benchmark.GPUbenchmark.MyGLRenderer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import com.wemarkbenches.cobenchmarkapp.R;
import com.wemarkbenches.cobenchmarkapp.main.MainActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class GPUActivity extends AppCompatActivity {

    CircularProgressButton circularProgressButton;
    private Button button;
    private ImageView image, cancelBtn;
    private CardView result;
    static private Context context;
    private boolean clicked = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_u);

        result = (CardView) findViewById(R.id.result);
        result.setVisibility(View.INVISIBLE);




        button = (Button) findViewById(R.id.btnback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(v.getId() == R.id.btnback) {
                    Intent intent = new Intent(GPUActivity.this, MainActivity.class);
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
                    Toast.makeText(GPUActivity.this, "Benchmark cancelled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GPUActivity.this, MainActivity.class);
                    startActivity(intent);
                    clicked = true;
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
                    @Override
                    protected String doInBackground(String... params) {
//                        if(MyGLRenderer.lastNrFrames != 0){
//                                return "Done!";
//                        }else {
                            Intent intent = new Intent(GPUActivity.this, GPUBenchmark.class);
                            startActivity(intent);
//                        }
                        return "Done!";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if(s.equals("Done!")) {
                            cancelBtn.setVisibility(View.INVISIBLE);
                            TextView MyScore = (TextView)findViewById(R.id.result_score);
                            MyScore.setText("Nr. frames"+String.valueOf(MyGLRenderer.lastNrFrames));

                            result.setVisibility(View.VISIBLE);
                            Toast.makeText(GPUActivity.this, "Benchmark done", Toast.LENGTH_SHORT).show();
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
