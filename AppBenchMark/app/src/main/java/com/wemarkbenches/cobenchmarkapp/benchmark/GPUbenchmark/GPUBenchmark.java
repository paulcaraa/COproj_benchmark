package com.wemarkbenches.cobenchmarkapp.benchmark.GPUbenchmark;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.wemarkbenches.cobenchmarkapp.Activities.GPUActivity;
import com.wemarkbenches.cobenchmarkapp.R;

public class GPUBenchmark extends AppCompatActivity{

    private GLSurfaceView glView;
    private Handler mHandler = new Handler();
    public static int nr = 5;

    // Call back when the activity is started, to initialize the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glView = new GLSurfaceView(this);           // Allocate a GLSurfaceView
        glView.setRenderer(new MyGLRenderer(this , nr)); // Use a custom renderer
        this.setContentView(glView);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GPUBenchmark.this, GPUActivity.class);
                startActivity(intent);
            }
        }, 4000); // 4 seconds
        nr++;
    }

    // Call back when the activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    // Call back after onPause()
    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }
}
