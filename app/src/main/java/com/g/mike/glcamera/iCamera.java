package com.g.mike.glcamera;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;

/**
 * Created by Mike on 7/6/2016.
 */
public interface iCamera {
    void setupCamera(int height, int width, SurfaceTexture surfaceTexture);
    void kill();
    void releaseCamera();
    void swapCam();
    void setParameters();
    void capture(int orientation);
    void toggleFlash();
    boolean getCameraId();
    int getWidth();
    int getHeight();
    void startPreview();
    int getCameraVersion();


}
