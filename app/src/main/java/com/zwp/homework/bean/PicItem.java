package com.zwp.homework.bean;

public class PicItem {

    public static final int TYPE_IMG = 0;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_DATE = 2;

    private String url;
    private long date;
    private String type;
    private boolean isSelected;
    private int locationX;
    private int locationY;
    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public PicItem(String url, long date, String type,int height,int width) {
        this.url = url;
        this.date = date;
        this.type = type;
        isSelected = false;
        this.width = width;
        this.height = height;
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

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
}
