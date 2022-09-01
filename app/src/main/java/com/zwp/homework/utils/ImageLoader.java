package com.zwp.homework.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImageLoader {

    HashMap<String, WeakReference<Bitmap>> activeRes;
    LruCache<String, Bitmap> cache;
    HandlerThread thread;
    Handler handler;

    public void initLoader() {
        thread = new HandlerThread("LOAD_IMAGE");
        thread.start();
        handler = new Handler(thread.getLooper());
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        activeRes = new HashMap<>();
        cache = new LruCache<String, Bitmap>(maxMemory / 8) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void load(String url, ImageView imageView) {
//        if (cache.get(item.getUrl()) != null) {
//            imageView.setImageBitmap(cache.get(item.getUrl()));
//        } else {
//            try {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                FileInputStream is = new FileInputStream(item.getUrl());
//                options.inSampleSize = 5;
//                Bitmap map = BitmapFactory.decodeStream(is, new Rect(0, 0, 0, 0), options);
//                cache.put(item.getUrl(), map);
//                return map;
//            } catch (Exception e) {
//                return null;
//            }
//        }

        if (activeRes.get(url) != null) {
            WeakReference<Bitmap> bitmapWeakReference = activeRes.get(url);
            if (bitmapWeakReference.get() != null) {
                imageView.setImageBitmap(bitmapWeakReference.get());
                return;
            } else {
                activeRes.remove(url);
            }
        }

        //active里面没有
        if (cache.get(url) != null) {
            Bitmap bitmap = cache.get(url);
            cache.remove(url);
            activeRes.put(url, new WeakReference<Bitmap>(bitmap));
        } else {
            //去本地加载
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        FileInputStream is = new FileInputStream(url);
                        options.inSampleSize = 5;
                        Bitmap map = BitmapFactory.decodeStream(is, null, options);
                        activeRes.put(url, new WeakReference<>(map));
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(map);
                            }
                        });
                    } catch (Exception e) {

                    }
                }
            });
        }
    }

    public void release() {
        if (thread != null) {
            thread.quit();
        }
        //bitmap recycle
    }
}
