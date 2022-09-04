package com.zwp.homework.bean;

import java.io.Serializable;

public class PicItem implements Serializable {

    public static final int TYPE_IMG = 0;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_DATE = 2;

    private String url;
    private long date;
    private String type;
    private boolean isSelected;
    private boolean hasLocationInfo;


    public void setHasLocationInfo(boolean hasLocationInfo) {
        this.hasLocationInfo = hasLocationInfo;
    }

    public boolean isHasLocationInfo() {
        return hasLocationInfo;
    }

    public int getType() {
        if (type.startsWith("video")) {
            return TYPE_VIDEO;
        } else if (type.startsWith("image")) {
            return TYPE_IMG;
        } else {
            return TYPE_DATE;
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public PicItem(String url, long date, String type) {
        this.url = url;
        this.date = date;
        this.type = type;
        isSelected = false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
