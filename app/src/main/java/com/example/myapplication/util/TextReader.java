package com.example.myapplication.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.myapplication.model.dto.MemoDTO;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.ViewerActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TextReader extends TextIO {

    public  TextReader(){
        this(null);
    }
    public TextReader(String path) {
        super(path);
        this.filelist = new ArrayList<File>();
    }

    public MemoDTO readData(int index) throws IOException {
        String body = "";
        StringBuffer bodytext = new StringBuffer();
        File selecttext = filelist.get(index);
        FileInputStream fis = new FileInputStream(selecttext);
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis, "euc-kr"));//pc의 txt파일 포맷을 지원할 수 있는 인코딩
        while ((body = bufferReader.readLine()) != null) {
            bodytext.append(body + "\n");
        }
        bufferReader.close();
        fis.close();
        String title = selecttext.getName();
        String content = bodytext.toString();
        int Idx = title.lastIndexOf("."); //뒤에 붙는 txt제거 문장. null일경우 팅김현상. 주의
        title = title.substring(0, Idx);
        MemoDTO dto = new MemoDTO(0,title, content,0);

        return dto;
    }
}
