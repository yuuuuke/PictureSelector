package com.zwp.homework.utils;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String stampToDate(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(s * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static boolean imageHasLocationInfo(String path) {
        if(path == null){
            return false;
        }
        try {
            if (path.startsWith("/storage/emulated/0/DCIM/Camera/")) {
                ExifInterface exifInterface = new ExifInterface(path);
                String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                //有信息，010101什么的，但不是null
                if (lngValue != null) {
                    return true;
                }
            }
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
