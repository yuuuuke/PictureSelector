package com.zwp.homework.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zwp.homework.R;
import com.zwp.homework.adapter.PicAdapter;
import com.zwp.homework.bean.PicItem;
import com.zwp.homework.loader.PicLoader;

import java.util.ArrayList;

public class SelectPicActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mList;
    private PicAdapter mAdapter;
    private ArrayList<PicItem> mData = new ArrayList<>(2000);

    private static final long ONE_HOUR = 60 * 60;
    private static final long ONE_DAY = 24 * ONE_HOUR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        mList = findViewById(R.id.list);
        mAdapter = new PicAdapter();
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    private void initData() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mData.get(position).getType() == PicItem.TYPE_DATE) {
                    return 3;
                } else {
                    return 1;
                }

            }
        });
        mAdapter.setData(mData);
        mList.setLayoutManager(manager);
        mList.setAdapter(mAdapter);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new PicLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        long lastTime = -1;
        //处理一下数据
        while (data.moveToNext()) {
            String url = data.getString(data.getColumnIndex(MediaStore.MediaColumns.DATA));
            long date = data.getLong(data.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
            String type = data.getString(data.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
            int height = data.getInt(data.getColumnIndex(MediaStore.Images.Media.HEIGHT));
            int width = data.getInt(data.getColumnIndex(MediaStore.Images.Media.WIDTH));
            if (lastTime == -1) {
                if (date % ONE_DAY > 16 * ONE_HOUR) {
                    //第二天了
                    lastTime = date - date % ONE_DAY + 16 * ONE_HOUR;
                } else {
                    lastTime = date - date % ONE_DAY - 8 * ONE_HOUR;
                }
                PicItem item = new PicItem(null, lastTime, "date", 0, 0);
                mData.add(item);
            } else {
                if ((lastTime - date) >= ONE_DAY) {
                    if (date % ONE_DAY > 16 * ONE_HOUR) {
                        //第二天了
                        lastTime = date - date % ONE_DAY + 16 * ONE_HOUR;
                    } else {
                        lastTime = date - date % ONE_DAY - 8 * ONE_HOUR;
                    }
                    PicItem item = new PicItem(null, lastTime, "date", 0, 0);
                    mData.add(item);
                }
            }
            PicItem item = new PicItem(url, date, type, height, width);
            mData.add(item);
        }
        initData();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
