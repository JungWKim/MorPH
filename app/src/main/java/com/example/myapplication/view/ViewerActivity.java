package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import com.example.myapplication.model.dto.MemoDTO;
import com.example.myapplication.presenter.Presenter;
import com.example.myapplication.presenter.symbolic_constants.Language;
import com.example.myapplication.presenter.symbolic_constants.Mode;

import java.io.File;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;


@SuppressWarnings("ConstantConditions")
public class ViewerActivity extends AppCompatActivity implements View.OnClickListener {
    // MainActivity 로부터 넘겨받은 메모 객체
    private MemoDTO mMemo;

    // 제목, 내용을 담당하는 EditText
    private EditText mTitleEditText, mContentsEditText;

    private int txt_mode;
    private View back_btn;//취소 버튼
    private Button play_btn_memo;//tts 시작
    private Button pause_btn_memo;
    private Button save_fab;
    private boolean initialized;
    private String queuedText;
    private String TAG = "TTS";
    private int mMode; // 0 : 뷰어 모드, 1 : 메모 추가 모드, 2 : 메모 수정 모드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        if (Build.VERSION.SDK_INT >= 21) getWindow().setStatusBarColor(Color.rgb(240, 172, 180));

        linkViews();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pause_btn_memo.setVisibility(View.INVISIBLE);
                pause_btn_memo.setClickable(false);
                play_btn_memo.setVisibility(View.VISIBLE);
                play_btn_memo.setClickable(true);
            }
        };
        Presenter.enableSTService(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
        receiveData();
        initViews();
        back_btn.setOnClickListener(btListener);
        play_btn_memo.setOnClickListener(btListener);
        pause_btn_memo.setOnClickListener(btListener);
        save_fab.setOnClickListener(this);


    }
    public void linkViews() {
        mTitleEditText = (EditText) findViewById(R.id.viewer_title_et);
        mContentsEditText = (EditText) findViewById(R.id.viewer_contents_et);
        pause_btn_memo = (Button) findViewById(R.id.pause_btn_memo);
        back_btn = findViewById(R.id.back_btn);
        play_btn_memo = (Button) findViewById(R.id.play_btn_memo);
        save_fab = (Button) findViewById(R.id.viewer_save_fab);
    }


    Button.OnClickListener btListener = new Button.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.back_btn:
                    Presenter.destructor();
                    onBackPressed();
                    break;
                case R.id.play_btn_memo:
                    Presenter.playService(mContentsEditText.getText().toString());
                    play_btn_memo.setVisibility(View.INVISIBLE);
                    play_btn_memo.setClickable(false);
                    pause_btn_memo.setVisibility(View.VISIBLE);
                    pause_btn_memo.setClickable(true);
                    break;
                case R.id.pause_btn_memo:
                    pause_btn_memo.setVisibility(View.INVISIBLE);
                    pause_btn_memo.setClickable(false);
                    play_btn_memo.setVisibility(View.VISIBLE);
                    play_btn_memo.setClickable(true);
                    Presenter.stopService();
                    break;
            }
        }

    };

    //TTS 객체 남아있을경우 삭제
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Presenter.destructor();
    }

    // 모드 설정을 위해 MainActivity 로부터 정보를 얻음
    private void receiveData() {
        mMemo = Presenter.getCurrentMemoDTO();
        mMode = Presenter.getCurrentMode();
    }


    // 뷰 초기화
    @SuppressLint("RestrictedApi")
    private void initViews() {
        //break 문이 없을 때 미끄러지는 특징 활용.
        // 추가모드 일때는 클릭 리스너만 세팅
        // 수정모드 일때는 기존 메모 값을 뷰에 세팅
        // 뷰어모드 일떄는 EditText 를 TextView 처럼 활용
        switch (mMode) {
            case Mode.READ :
                mTitleEditText.setEnabled(false);
                mContentsEditText.setEnabled(false);
                save_fab.setVisibility(View.INVISIBLE);
            case Mode.UPDATE :
                mTitleEditText.setText(mMemo.getTitle());
                mContentsEditText.setText(mMemo.getContents());
            case Mode.INSERT :
        }
    }

    // 뒤로가기 버튼을 눌렀을 때 result 세팅
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    // 메모 추가 또는 수정 모드일 때 버튼 클릭시 메모 내용 체크 후 MainActivity 로 전달
    @Override
    public void onClick(View v) {
        String title = mTitleEditText.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "제목을 입력해 주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        String contents = mContentsEditText.getText().toString();
        if (TextUtils.isEmpty(contents))
            Toast.makeText(this, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show();

        mMemo.setTitle(title);
        mMemo.setContents(contents);
        mMemo.setTime(System.currentTimeMillis());

        setResult(RESULT_OK, new Intent().putExtra("memo", mMemo).putExtra("mode", mMode));
        finish();
    }
}