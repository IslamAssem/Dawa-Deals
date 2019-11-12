package me.hashcode.dawadeals.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import me.hashcode.dawadeals.App;

@SuppressWarnings("unused")
class CheckPermissions {

    public static final int PERMISSIONS_REQUEST_CAMERA = 300;
    public static final int PERMISSIONS_REQUEST_LOCATION = 400;


    public static boolean is_CAMERA_PermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            return  (App.getInstance().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        }
        else {
            // Permission is granted by default
            return true;
        }

    }


    public static boolean is_STORAGE_PermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            return  (App.getInstance().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        }
        else {
            // Permission is granted by default
            return true;
        }

    }


    public static boolean is_LOCATION_PermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            return  (App.getInstance().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        }
        else {
            // Permission is granted by default
            return true;
        }

    }


    public static boolean is_PHONE_STATE_PermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            return  (App.getInstance().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
        }
        else {
            // Permission is granted by default
            return true;
        }

    }
}
