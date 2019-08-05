package com.example.myapplication.model.dto;

import java.io.Serializable;

public class MemoDTO implements Serializable {
    // 메모 id
    private int id;
    // 메모 제목
    private String title;
    // 메모 내용
    private String contents;
    // 메모 시간
    private long time;

    public MemoDTO() {
    }
    public MemoDTO(String title, String contents) {
        this(0,title,contents,0);
    }
    public MemoDTO(int id, String title, String contents, long time) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Memo{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", contents='").append(contents).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}