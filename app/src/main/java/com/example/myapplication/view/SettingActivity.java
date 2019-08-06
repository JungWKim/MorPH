package com.example.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class SettingActivity extends Activity {
    TextView toneValue;
    TextView speedValue;
    TextView delayValue;

    Button toneUp, toneDown;
    Button speedUp, speedDown;
    Button delayUp, delayDown;
    View back_btn;
    float tone = 1.0f, speed = 1.0f;//목소리톤과 말하기 속도 값
    int languageNum;
    int delay = 900;
    int toneOnScreen = 10, speedOnScreen = 10, delayOnScreen = 900;

    Spinner spinner;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle bundle = new Bundle();

        //화면이 회전하거나 다른 액티비티로 이동할 때 값들을 bundle에 저장
        bundle.putInt("toneOnScreenBundle", toneOnScreen);
        bundle.putInt("speedOnScreenBundle", speedOnScreen);
        bundle.putInt("languageBundle", languageNum);
        bundle.putFloat("toneBundle", tone);
        bundle.putFloat("speedBundle", speed);
        bundle.putInt("delayBundle",delay);

        outState.putParcelable("bundleValue", bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //시작했을때 bundle에 저장된 값들을 가져옴
        if(savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getParcelable("bundleValue");

            if(bundle != null) {
                toneOnScreen = bundle.getInt("toneOnScreenBundle");
                speedOnScreen = bundle.getInt("speedOnScreenBundle");
                delayOnScreen = bundle.getInt("delayOnScreenBundle");
                tone = bundle.getFloat("toneBundle");
                speed = bundle.getFloat("speedBundle");
                languageNum = bundle.getInt("languageBundle");
                delay = bundle.getInt("delayBundle");
            }
        }

        if (Build.VERSION.SDK_INT >= 21) getWindow().setStatusBarColor(Color.rgb(141,180,216));

        toneValue = findViewById(R.id.toneValue);
        speedValue = findViewById(R.id.speedValue);
        delayValue = findViewById(R.id.delayValue);
        //bundle로 가져온 데이터를 화면에 표시
        toneValue.setText(String.valueOf(toneOnScreen));
        speedValue.setText(String.valueOf(speedOnScreen));
        delayValue.setText(String.valueOf(delayOnScreen));

        //SharedPreference로 값들을 받아오는 부분
        SharedPreferences sharedPreferences = getSharedPreferences("DataShared", MODE_PRIVATE);
        toneOnScreen = sharedPreferences.getInt("toneOnScreenShared", 10);
        toneValue.setText(String.valueOf(toneOnScreen));
        speedOnScreen = sharedPreferences.getInt("speedOnScreenShared", 10);
        speedValue.setText(String.valueOf(speedOnScreen));
        delayOnScreen = sharedPreferences.getInt("delayOnScreenShared",900);
        delayValue.setText(String.valueOf(delayOnScreen));
        tone = sharedPreferences.getFloat("toneShared", 1.0f);
        speed = sharedPreferences.getFloat("speedShared", 1.0f);
        languageNum = sharedPreferences.getInt("languageShared", 1);
        delay = sharedPreferences.getInt("delayShared",900);

        toneUp = findViewById(R.id.toneUp);
        toneDown = findViewById(R.id.toneDown);
        speedUp = findViewById(R.id.speedUp);
        speedDown = findViewById(R.id.speedDown);
        delayUp = findViewById(R.id.delayUp);
        delayDown = findViewById(R.id.delayDown);
        back_btn =  findViewById(R.id.back_btn);
        spinner = findViewById(R.id.spinner);
        Intent intent = getIntent();
        String[] list = {"Korean", "English", "Japanese", "Chinese"};
        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        spinner.setAdapter(spAdapter);

        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onStop();
                finish();
            }
        });

        //목소리톤 올리는 버튼리스너
        toneUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toneOnScreen >= 20) {
                    toneOnScreen = 20;
                    tone = 2.0f;
                    Toast.makeText(getApplicationContext(), "최대치에 도달했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    tone += 0.1f;
                    toneOnScreen++;
                    Toast.makeText(getApplicationContext(), "목소리톤이 올라갔습니다.", Toast.LENGTH_SHORT).show();
                }
                toneValue.setText(String.valueOf(toneOnScreen));
            }
        });

        //목소리톤 내리는 버튼리스너
        toneDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toneOnScreen <= 1) {
                    toneOnScreen = 1;
                    tone = 0.1f;
                    Toast.makeText(getApplicationContext(), "최저치에 도달했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    tone -= 0.1f;
                    toneOnScreen--;
                    Toast.makeText(getApplicationContext(), "목소리톤이 내려갔습니다.", Toast.LENGTH_SHORT).show();
                }
                toneValue.setText(String.valueOf(toneOnScreen));
            }
        });

        //말하기 속도 올리는 버튼리스너
        speedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speedOnScreen >= 20) {
                    speedOnScreen = 20;
                    speed = 2.0f;
                    Toast.makeText(getApplicationContext(), "최대치에 도달했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    speed += 0.1f;
                    speedOnScreen++;
                    Toast.makeText(getApplicationContext(), "말하기 속도가 올라갔습니다.", Toast.LENGTH_SHORT).show();
                }
                speedValue.setText(String.valueOf(speedOnScreen));
            }
        });

        //말하기 속도 내리는 버튼리스너
        speedDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speedOnScreen <= 1) {
                    speedOnScreen = 1;
                    speed = 0.1f;
                    Toast.makeText(getApplicationContext(), "최저치에 도달했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    speed -= 0.1f;
                    speedOnScreen--;
                    Toast.makeText(getApplicationContext(), "말하기 속도가 내려갔습니다.", Toast.LENGTH_SHORT).show();
                }
                speedValue.setText(String.valueOf(speedOnScreen));
            }
        });

        delayUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delayOnScreen >= 2000) {
                    delayOnScreen = 2000;
                    delay = 2000;
                    Toast.makeText(getApplicationContext(), "최대치에 도달했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    delay += 50;
                    delayOnScreen += 50;
                    Toast.makeText(getApplicationContext(), ".에 대한 딜레이가 증가합니다.", Toast.LENGTH_SHORT).show();
                }
                delayValue.setText(String.valueOf(delayOnScreen));
            }
        });

        //말하기 속도 내리는 버튼리스너
        delayDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delayOnScreen <= 50) {
                    delayOnScreen = 50;
                    delay = 50;
                    Toast.makeText(getApplicationContext(), "최저치에 도달했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    delay -= 50;
                    delayOnScreen -=50;
                    Toast.makeText(getApplicationContext(), ".에 대한 딜레이가 감소합니다.", Toast.LENGTH_SHORT).show();
                }
                delayValue.setText(String.valueOf(delayOnScreen));
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    languageNum = 1;
                } else if (position == 1) {
                    languageNum = 2;
                } else if (position == 2) {
                    languageNum = 3;
                } else {
                    languageNum = 4;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    //앱이 종료되었을 때 SharedPreferences에 값들 저장
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("DataShared", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("toneOnScreenShared", toneOnScreen);
        editor.putInt("speedOnScreenShared", speedOnScreen);
        editor.putInt("delayOnScreenShared",delayOnScreen);
        editor.putInt("languageShared", languageNum);
        editor.putInt("delayShared",delay);
        editor.putFloat("toneShared", tone);
        editor.putFloat("speedShared", speed);

        editor.commit();
    }
}