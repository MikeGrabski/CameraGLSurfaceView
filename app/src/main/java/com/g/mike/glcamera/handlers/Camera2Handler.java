package com.g.mike.glcamera.handlers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;

import com.g.mike.glcamera.iCamera;

/**
 * Created by Mike on 7/8/2016.
 */
//@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2Handler implements iCamera {

    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private String[] camerIDs;
    private Context context;
    private boolean cameraFront;
    private Camera


    @Override
    public void setupCamera(int height, int width, SurfaceTexture surfaceTexture) {
        cameraManager = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
        try {
           camerIDs= cameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics
                        = cameraManager.getCameraCharacteristics(cameraId);

                // We don't use a front facing camera in this sample.
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
            }
        }
        catch (Exception e){

        }
    }

    @Override
    public void kill() {

    }

    @Override
    public void releaseCamera() {

    }

    @Override
    public void swapCam() {

    }

    @Override
    public void openCamera() {

    }

    @Override
    public void setParameters() {

    }

    @Override
    public void capture(int orientation) {

    }

    @Override
    public void toggleFlash() {

    }

    @Override
    public boolean getCameraId() {
        return false;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void startPreview() {

    }
}
