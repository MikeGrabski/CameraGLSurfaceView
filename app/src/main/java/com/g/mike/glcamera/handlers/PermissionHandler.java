package com.g.mike.glcamera.handlers;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Mike on 7/4/2016.
 */
public class PermissionHandler {
    private static final int PERMISSIONS_REQUEST_EXTSD = 1;
    private static final int PERMISSIONS_REQUEST_FLASH = 2;
    private static final int PERMISSIONS_REQUEST_CAMERA = 3;

    public static boolean getCameraPermission(Activity activity){
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        }
        else return true;
        return false;
    }

    public static boolean getFlashPermission(Activity activity){

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.FLASHLIGHT)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.FLASHLIGHT},
                    PERMISSIONS_REQUEST_FLASH);
        }
        else return true;

        return false;
    }

    public static boolean getExtStoragePermission(Activity activity){

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_EXTSD);
        }
        else {
            return true;
        }
        return false;
    }

}
