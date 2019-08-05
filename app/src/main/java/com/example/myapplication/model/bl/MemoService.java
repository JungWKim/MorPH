package com.example.myapplication.model.bl;

import android.content.Context;
import android.os.Environment;

import com.example.myapplication.presenter.symbolic_constants.Permisson;
import com.example.myapplication.model.bl.Service;
import com.example.myapplication.model.dao.MemoDAO;
import com.example.myapplication.model.dto.MemoDTO;
import com.example.myapplication.util.MemoAdapter;

import java.io.File;
import java.util.ArrayList;

public class MemoService extends Service {
    private MemoDAO memoDAO;
    private MemoDTO currentMemoDTO;
    private int currentMode;
    private String path;
    public MemoService(){}
    public MemoService(Context context) {
        super(context);
        memoDAO = new MemoDAO(context);
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/morPH";
    }

    public MemoAdapter getMemoAdapter() {
        ArrayList<MemoDTO> memoArrayList = memoDAO.findAll();
        return new MemoAdapter(memoArrayList);
    }
    public boolean dataSave(int requestCode, int mode,MemoDTO memo) {
        if (requestCode == Permisson.REQUEST_WRITE ) {
            switch (mode) {
                // 메모 저장
                case 1:
                    memoDAO.save(memo);
                    break;
                // 메모 수정
                case 2:
                    memoDAO.update(memo);
                    break;
            }
        }
        return true;
    }
    public boolean delete(int id) {
        return memoDAO.delete(id);
    }
    public boolean swapData(MemoAdapter adapter) {
        adapter.swapData(memoDAO.findAll());
        return true;
    }

    public void setCurrentMemoDTO(MemoDTO memoDTO) {
        this.currentMemoDTO = memoDTO;
    }

    public MemoDTO getCurrentMemoDTO() {
        return this.currentMemoDTO;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public int getCurrentMode() {
        return currentMode;
    }
}
