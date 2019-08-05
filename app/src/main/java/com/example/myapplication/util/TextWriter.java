package com.example.myapplication.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextWriter extends TextIO {
    public TextWriter(){
        this(null);
    }
    public TextWriter(String path) {
        super(path);
    }
    public boolean writeData(String title, String body) throws IOException {
        FileOutputStream fos = new FileOutputStream(files);
        BufferedWriter buw = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));
        buw.write(body);
        buw.close();
        fos.close();
        return true;
    }
}
