package com.zwp.homework.utils;

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
}
