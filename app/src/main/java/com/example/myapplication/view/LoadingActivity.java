package com.example.myapplication.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.R;

public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}