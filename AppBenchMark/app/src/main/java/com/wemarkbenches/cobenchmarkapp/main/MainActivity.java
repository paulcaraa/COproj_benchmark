package com.wemarkbenches.cobenchmarkapp.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wemarkbenches.cobenchmarkapp.Activities.CPUActivity;
import com.wemarkbenches.cobenchmarkapp.Activities.GPUActivity;
import com.wemarkbenches.cobenchmarkapp.Activities.MEMActivity;
import com.wemarkbenches.cobenchmarkapp.Activities.NETActivity;
import com.wemarkbenches.cobenchmarkapp.R;
import com.wemarkbenches.cobenchmarkapp.Activities.RAMActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     GLSurfaceView view = new GLSurfaceView(this);
     //   view.setRenderer(new TestRender());
     //   setContentView(view);
        setContentView(R.layout.activity_main);

        View v = findViewById(R.id.button1);
        v.setOnClickListener(this);

        View v2 = findViewById(R.id.button2);
        v2.setOnClickListener(this);

        View v3 = findViewById(R.id.button3);
        v3.setOnClickListener(this);

        View v4 = findViewById(R.id.button4);
        v4.setOnClickListener(this);

        View v5 = findViewById(R.id.button5);
        v5.setOnClickListener(this);

        View vexit = findViewById(R.id.buttonExit);
        vexit.setOnClickListener(this);
    }


    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.button1) {
            Intent intent = new Intent(this, CPUActivity.class);
            this.startActivity(intent);
        }

        if(arg0.getId() == R.id.button2) {
            Intent intent = new Intent(this, GPUActivity.class);
            this.startActivity(intent);
        }

        if(arg0.getId() == R.id.button3) {
            Intent intent = new Intent(this, MEMActivity.class);
            this.startActivity(intent);
        }

        if(arg0.getId() == R.id.button4) {
            Intent intent = new Intent(this, RAMActivity.class);
            this.startActivity(intent);
        }

        if(arg0.getId() == R.id.button5) {
            Intent intent = new Intent(this, NETActivity.class);
            this.startActivity(intent);
        }

        if(arg0.getId() == R.id.buttonExit) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }
}
