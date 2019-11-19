package me.hashcode.dawadeals.utils;


import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import java.io.File;

public class RealPathUtil {
    static final String TAG = "GalleryPhoto";


    public static File getFileFromUri(Context context, Uri fileUri) {
        return new File(RealPathUtil.getRealPathFromURI_API19(context, fileUri));
    }
    @SuppressLint({"NewApi"})
    public static String getRealPathFromURI_API19(Context context, Uri photoUri) {
        String path = "";
        if (DocumentsContract.isDocumentUri(context, photoUri)) {
            String docId;
            String[] split;
            String type;
            if (isExternalStorageDocument(photoUri)) {
                docId = DocumentsContract.getDocumentId(photoUri);
                split = docId.split(":");
                type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(photoUri)) {
                docId = DocumentsContract.getDocumentId(photoUri);
                Uri split1 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getDataColumn(context, split1, null, null);
            } else if (isMediaDocument(photoUri)) {
                docId = DocumentsContract.getDocumentId(photoUri);
                split = docId.split(":");
                type = split[0];
                Uri contentUri = null;
                switch (type) {
                    case "image":
                        contentUri = Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                }

                String selection = "_id=?";
                String[] selectionArgs = new String[]{split[1]};
                path = getDataColumn(context, contentUri, "_id=?", selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(photoUri.getScheme())) {
            path = getDataColumn(context, photoUri, null, null);
        } else if ("file".equalsIgnoreCase(photoUri.getScheme())) {
            path = photoUri.getPath();
        }

        return path;
    }

    @SuppressLint({"NewApi"})
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        Log.e("GalleryPhoto", "API 11 to 18");
        String[] proj = new String[]{"_data"};
        String result = null;
        CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }

        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        Log.e("GalleryPhoto", "API Below 11");
        String path;
        String[] proj = new String[]{"_data"};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = new String[]{"_data"};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow("_data");
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
