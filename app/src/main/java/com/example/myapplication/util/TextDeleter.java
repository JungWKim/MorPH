package com.example.myapplication.util;

public class TextDeleter extends TextIO {
    public TextDeleter(){
        this(null);
    }
    public TextDeleter(String path) {
        super(path);
    }
    public boolean deleteData(int index) {
        filelist.get(index).delete();
        return true;
    }
}
