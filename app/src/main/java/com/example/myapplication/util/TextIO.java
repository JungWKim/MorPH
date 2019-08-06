package com.example.myapplication.util;

import java.io.File;
import java.util.ArrayList;

public class TextIO {
    protected File files;
    protected String path;
    protected ArrayList<File> filelist;
    protected TextIO(){}
    protected TextIO(String path) {
        this.path = path;
        this.files = new File(this.path);
    }
    public boolean fileExists() {
        return files.exists();
    }
    public boolean fileLengthCheck() {
        return files.listFiles().length>0;
    }
    public void fileMkdirs() {fileMkdirs();}

    public CharSequence[] getFileNames(){
        for (File file : files.listFiles(new TextFileFilter())) filelist.add(file);
        final CharSequence[] filename = new CharSequence[filelist.size()];
        for (int i = 0; i < filelist.size(); i++) {
            filename[i] = filelist.get(i).getName();
        }
        return filename;
    }
}
