package com.example.myapplication.model.bl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.RequiresApi;

import com.example.myapplication.presenter.symbolic_constants.Language;

import java.util.HashMap;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class STService extends Service {
    private TextToSpeech tts;
    private float speed, tone;
    private int language, delay;
    public STService(Context context) {
        super(context);
    }

    public void enableSTService(final UtteranceProgressListener listener) {
        SharedPreferences sp = context.getSharedPreferences("DataShared", context.MODE_PRIVATE);
        speed = sp.getFloat("speedShared", 1.0f);
        tone = sp.getFloat("toneShared", 1.0f);
        language = sp.getInt("languageShared", 1);
        delay = sp.getInt("delayShared", 900);
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    if (language == Language.KOREAN) tts.setLanguage(Locale.KOREAN);
                    if (language == Language.ENGLISH) tts.setLanguage(Locale.ENGLISH);
                    if (language == Language.JAPANESE) tts.setLanguage(Locale.JAPANESE);
                    if (language == Language.CHINESE) tts.setLanguage(Locale.CHINESE);
                }
                tts.setOnUtteranceProgressListener(listener);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playService(String text) {
        tts.setPitch(tone); //음성톤 설정
        tts.setSpeechRate(speed); //읽는 속도 설정
        String[] splitspeech = text.split("\\.");
        for (int i = 0; i < splitspeech.length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            if (i == 0) // Use for the first splited text to flush on audio stream
                tts.speak(splitspeech[i].trim(), tts.QUEUE_FLUSH, map);
            else // add the new test on previous then play the TTS
                tts.speak(splitspeech[i].trim(), tts.QUEUE_ADD, map);
            tts.playSilentUtterance(delay, tts.QUEUE_ADD,TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
        }
    }
    public void stopService() {
        tts.stop();
        tts.shutdown();
    }

    public boolean isTTSSpeaking() {
        if(tts == null) return false;
        return tts.isSpeaking();
    }

    public void destructor() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}
