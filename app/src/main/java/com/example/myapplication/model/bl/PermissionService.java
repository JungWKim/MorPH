package com.example.myapplication.model.bl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.myapplication.presenter.symbolic_constants.Permisson;

import java.util.ArrayList;
import java.util.List;

public class PermissionService extends Service{

    public PermissionService() {}
    public PermissionService(Context context) {
        super(context);
    }
    public void showCheckPermission(final Activity activity) {
        //안드로이드버전 6.0이상인 경우 퍼미션 체크 & 3.5초 동안 로딩화면보이기
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 23)
                    checkPermission(activity);
            }
        }, 3500 );
    }
    private boolean checkPermission(Activity activity) {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : Permisson.permissions) {
            result = ContextCompat.checkSelfPermission(context, pm);
            if (result != PackageManager.PERMISSION_GRANTED) permissionList.add(pm);
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), Permisson.MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Permisson.MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(Permisson.permissions[i])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) return false;
                        }
                    }
                } else return false;
            }
        }
        return true;
    }
}
