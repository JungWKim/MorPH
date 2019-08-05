package com.example.myapplication.util;

import java.io.File;
import java.io.FileFilter;

//.txt파일만을 보여주도록 한다.
class TextFileFilter implements FileFilter {
    public boolean accept(File file) {
        // TODO Auto-generated method stub
        if (file.getName().endsWith(".txt")) return true;
        return false;
    }
}