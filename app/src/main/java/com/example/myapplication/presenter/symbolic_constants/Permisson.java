package com.example.myapplication.presenter.symbolic_constants;

import android.Manifest;

public class Permisson {
    public static final String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    public static final int MULTIPLE_PERMISSIONS = 101;//퍼미션 요구
    public static final int REQUEST_WRITE = 1000;


}
