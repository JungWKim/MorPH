package com.example.myapplication.util;

import android.provider.BaseColumns;

public class MemoDbContract {
    public static class MemoDbEntry implements BaseColumns {
        public static final String TABLE_NAME = "MemoDb";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENTS = "contents";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
