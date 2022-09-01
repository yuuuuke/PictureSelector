package com.zwp.homework.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zwp.homework.R;
import com.zwp.homework.utils.ImageLoader;
import com.zwp.homework.utils.Utils;
import com.zwp.homework.bean.PicItem;

import java.util.ArrayList;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.BaseHolder> {

    private ArrayList<PicItem> mData;
    public ImageLoader loader;

    public void setData(ArrayList<PicItem> data) {
        loader = new ImageLoader();
        loader.initLoader();
        this.mData = data;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == PicItem.TYPE_IMG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_item, parent, false);
            return new PicAndVideoHolder(view);
        } else if (viewType == PicItem.TYPE_VIDEO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_item, parent, false);
            return new PicAndVideoHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_date_item, parent, false);
            return new DateHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class PicAndVideoHolder extends BaseHolder {

        private ImageView ivPic;

        public PicAndVideoHolder(@NonNull View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_image);
        }

        @Override
        public void bindView(PicItem item) {
            loader.load(item.getUrl(), ivPic);

        }
    }

    static class DateHolder extends BaseHolder {

        private TextView mTvDate;

        public DateHolder(@NonNull View itemView) {
            super(itemView);
            mTvDate = itemView.findViewById(R.id.tv_date);
        }

        @Override
        public void bindView(PicItem item) {
            mTvDate.setText(Utils.stampToDate(item.getDate()));
        }
    }

    abstract static class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
        }

        abstract public void bindView(PicItem item);
    }
}
