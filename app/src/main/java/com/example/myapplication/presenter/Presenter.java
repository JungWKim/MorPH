package com.example.myapplication.presenter;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.UtteranceProgressListener;

import com.example.myapplication.model.MemoService;
import com.example.myapplication.model.bl.PermissionService;
import com.example.myapplication.model.bl.STService;
import com.example.myapplication.model.bl.TextService;
import com.example.myapplication.model.dto.MemoDTO;
import com.example.myapplication.util.MemoAdapter;

import java.io.IOException;

public class Presenter {
    private static MemoService memoService;
    private static PermissionService permissionService;
    private static TextService textService;
    private static STService stService;

    public static void registerContext(Context context) {
        memoService = new MemoService(context);
        permissionService = new PermissionService(context);
        textService = new TextService(context);
        stService = new STService(context);
    }
   //memoService
   public static MemoAdapter getMemoAdapter() {
        return memoService.getMemoAdapter();
   }
   public static boolean delete(int id) {
        return memoService.delete(id);
    }
   public static boolean dataSave(int requestCode, int mode,MemoDTO memo) {
        return memoService.dataSave(requestCode, mode, memo);
   }
    public static boolean swapData(MemoAdapter adapter) {
        return memoService.swapData(adapter);
    }

    public static void setCurrentMemoDTO(MemoDTO memoDTO) {
        memoService.setCurrentMemoDTO(memoDTO);
    }

    public static MemoDTO getCurrentMemoDTO() {
        return memoService.getCurrentMemoDTO();
    }

    public static void setCurrentMode(int currentMode) {
        memoService.setCurrentMode(currentMode);
    }

    public static int getCurrentMode() {
        return memoService.getCurrentMode();
    }
   //textService
    public static boolean enableTextReader() {
        return textService.enableTextReader();
    }
    public static boolean enableTextWriter() {
        return textService.enableTextWriter();
    }
    public static boolean enableTextDeleter() {
        return textService.enableTextDeleter();
    }
    public static CharSequence[] getFileNamesForReading() {
        return textService.getFileNamesForReading();
    }
    public static CharSequence[] getFileNamesForDeleting() {
        return textService.getFileNamesForDeleting();
    }
    public static MemoDTO readData(int index) {
        MemoDTO memoDTO = null;
        try {
            memoDTO = textService.readData(index);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return memoDTO;
    }

    public static boolean writeData(String title, String body) {
        boolean flag = false;
        try {
            flag = textService.writeData(title,body);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public static boolean deleteData(int index) {
        return textService.deleteData(index);
    }

    //permissionSerivce
    public static void showCheckPermission(final Activity activity) {
        permissionService.showCheckPermission(activity);
    }
    public static boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        return permissionService.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //stService
    public static void enableSTService(UtteranceProgressListener listener) {
        stService.enableSTService(listener);
    }

    public static void playService(String text) {
        stService.playService(text);
    }
    public static void stopService() {
        stService.stopService();
    }
    public static boolean isTTSSpeaking() {
       return stService.isTTSSpeaking();
    }

    public static void destructor() {
        stService.destructor();
    }
}
