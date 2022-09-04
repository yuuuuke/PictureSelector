package com.zwp.homework.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;

import com.zwp.homework.R;
import com.zwp.homework.bean.PicItem;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImageLoader {

    HashMap<String, WeakReference<Bitmap>> activeRes;
    LruCache<String, Bitmap> cache;

    private HashMap<ImageView, String> imgTask;

    public void initLoader() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        activeRes = new HashMap<>();
        imgTask = new HashMap<>();
        cache = new LruCache<String, Bitmap>(maxMemory / 8) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void load(PicItem item, ImageView imageView) {
        String url = item.getUrl();
        imgTask.put(imageView, url);
        if (activeRes.get(url) != null) {
            WeakReference<Bitmap> bitmapWeakReference = activeRes.get(url);
            if (bitmapWeakReference.get() != null) {
                setImageBitmap(imageView, url, bitmapWeakReference.get());
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

            setImageBitmap(imageView, url, bitmap);
        } else {
            //去本地加载
            imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.getContext().getResources(), R.drawable.default_img, null));
            ThreadPoolHelper.getInstance().RunOnIoThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bmp;
                        if(item.getType() == PicItem.TYPE_IMG){
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(url, options);
                            int temp = Math.min(options.outWidth, options.outHeight);
                            options.inSampleSize = temp / 400 + 1;
                            options.inJustDecodeBounds = false;
                            bmp = BitmapFactory.decodeFile(url, options);
                        }else{
                            //视频
                            MediaMetadataRetriever mmr=new MediaMetadataRetriever();
                            mmr.setDataSource(url);
                            bmp = mmr.getFrameAtTime(0);
                            mmr.close();
                        }

                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                setImageBitmap(imageView, url, bmp);
                                activeRes.put(url, new WeakReference<>(bmp));
                            }
                        });
                    } catch (Exception e) {

                    }
                }
            });
        }
    }

    private void setImageBitmap(ImageView iv, String url, Bitmap bmp) {
        if (url.equals(imgTask.get(iv))) {
            iv.setImageBitmap(bmp);
        }
    }

    public void detachImg(String url) {
        if (activeRes.get(url) != null && activeRes.get(url).get() != null) {
            Bitmap bitmap = activeRes.get(url).get();
            activeRes.remove(url);
            cache.put(url, bitmap);
        }
    }
}
