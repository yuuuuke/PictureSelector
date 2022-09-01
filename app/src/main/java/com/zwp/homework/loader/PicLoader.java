package com.zwp.homework.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

public class PicLoader extends CursorLoader {

    private static final Uri URI = MediaStore.Files.getContentUri("external");

    private static final String[] PROJ = new String[]{
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_ADDED,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.WIDTH,
    };

    private static final String[] SELECTION_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
    };

    private static final String SELECTION =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.Video.Media.DURATION + ">1000)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">1024";

    public PicLoader(Context context) {
        super(context, URI, PROJ, SELECTION, SELECTION_ARGS, MediaStore.Images.ImageColumns.DATE_ADDED + " DESC");
    }
}
