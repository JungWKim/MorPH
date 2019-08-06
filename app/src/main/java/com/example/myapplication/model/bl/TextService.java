package com.example.myapplication.model.bl;

import android.content.Context;
import android.os.Environment;

import com.example.myapplication.model.dto.MemoDTO;
import com.example.myapplication.util.TextDeleter;
import com.example.myapplication.util.TextReader;
import com.example.myapplication.util.TextWriter;

import java.io.File;
import java.io.IOException;

public class TextService extends Service {
    private String path;
    private TextReader textReader;
    private TextWriter textWriter;
    private TextDeleter textDeleter;
    private MemoDTO currentReadData;

    public TextService() {
        this(null);
    }

    public TextService(Context context) {
        super(context);
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/morPH";
        textReader = new TextReader(path);
        textWriter = new TextWriter(path);
        textDeleter = new TextDeleter(path);
    }

    public CharSequence[] getFileNamesForReading() {
        return textReader.getFileNames();
    }

    public CharSequence[] getFileNamesForDeleting() {
        return textDeleter.getFileNames();
    }

    public boolean enableTextReader() {
        if (!textReader.fileExists()) textReader.fileMkdirs();
        textReader.fileLengthCheck();
        return true;
    }

    public boolean enableTextWriter() {
        if (!textWriter.fileExists()) textWriter.fileMkdirs();
        textWriter.fileLengthCheck();
        return true;
    }

    public boolean enableTextDeleter() {
        if (!textDeleter.fileExists()) textDeleter.fileMkdirs();
        textDeleter.fileLengthCheck();
        return true;
    }

    public MemoDTO readData(int index) throws IOException {
        currentReadData = textReader.readData(index);
        return currentReadData;
    }

    public boolean writeData(String title, String body) throws IOException {
        return textWriter.writeData(title, body);
    }

    public boolean deleteData(int index) {
        return textDeleter.deleteData(index);
    }
}
