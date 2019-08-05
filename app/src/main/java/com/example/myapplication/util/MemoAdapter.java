package com.example.myapplication.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.dto.MemoDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MemoAdapter extends BaseAdapter {
    private ArrayList<MemoDTO> mData;
    private SimpleDateFormat mSimpleDateFormat;

    public MemoAdapter(ArrayList<MemoDTO> mData) {
        this.mData = mData;
        mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN);
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        } else {
            return mData.size();
        }
    }

    // 새 데이터로 교체 후 화면 갱신
    public void swapData(ArrayList<MemoDTO> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MemoDTO item = (MemoDTO) getItem(position);
        holder.mTitleTextView.setText(item.getTitle());
        holder.mContentsTextView.setText(item.getContents());
        holder.mTimeTextView.setText(mSimpleDateFormat.format(new Date(item.getTime())));

        return convertView;
    }

    private static final class ViewHolder {
        TextView mTitleTextView, mContentsTextView, mTimeTextView;

        ViewHolder(View itemView) {
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_memo_title_tv);
            mContentsTextView = (TextView) itemView.findViewById(R.id.item_memo_contents_tv);
            mTimeTextView = (TextView) itemView.findViewById(R.id.item_memo_time_tv);
        }
    }
}