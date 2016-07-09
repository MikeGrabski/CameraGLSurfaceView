package com.g.mike.glcamera;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.g.mike.glcamera.gl.CameraRenderer;
import com.g.mike.glcamera.handlers.CameraHandler;
import com.g.mike.glcamera.handlers.PermissionHandler;
import com.littlecheesecake.shadercameraexample.R;


/**
 * Created by Mike on 6/29/2016.
 */
public class DisplayFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_FLASH= 2;
    private static final int PERMISSIONS_REQUEST_CAMERA = 3;
    private static final int PERMISSIONS_REQUEST_EXTSD = 1;

    private CameraRenderer mRenderer;
    private Switch aSwitch;
    private boolean effectSet = false;
    private int splitPosition = 0;
    private boolean flashAvailable = false;
    private FrameLayout f;
    private ImageButton splitButton;
    private ImageButton swapCamera;
    private ImageButton capture;
    private ImageButton flashMode;
    private ProgressBar progressBar;
private     Context context;
private     OrientationEventListener oEL;
    private int currentOrientation;
    private boolean flashEnabled = false;
    private boolean extPermissionGranted;
private iCamera iCam;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oEL = new OrientationEventListener(getActivity().getApplicationContext(), SensorManager.SENSOR_DELAY_UI) {
            @Override
            public void onOrientationChanged(int orientation) {
                setOrientation(orientation);
            }
        };
        PermissionHandler.getCameraPermission(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getActivity().getApplicationContext();
        View view = inflater.inflate( R.layout.fragment_display,container,false);

        aSwitch = (Switch)view.findViewById(R.id.switch1);
        mRenderer = new CameraRenderer(context);
        splitButton = (ImageButton)view.findViewById(R.id.button);
        capture=(ImageButton)view.findViewById(R.id.capture);
        progressBar = (ProgressBar)view.findViewById(R.id.processing);
        f = (FrameLayout)view.findViewById(R.id.glView);
        f.addView(mRenderer);
        // mRenderer = (CameraRenderer)findViewById(R.id.renderer_view);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                effectChange(isChecked);
            }
        });
        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSplitPosition();
            }
        });
        if(Camera.getNumberOfCameras()>1){
            swapCamera = (ImageButton)view.findViewById(R.id.buttonSwap);
            swapCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swapCamera();
                }
            });
            swapCamera.setVisibility(ImageButton.VISIBLE);
        }
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            flashMode = (ImageButton)view.findViewById(R.id.flash);
            flashMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFlash();
                }
            });
            flashMode.setVisibility(ImageButton.VISIBLE);
            flashAvailable = true;
        }

        return view;
    }

    private void swapCamera() {
        mRenderer.swapCamera();
        if (flashAvailable) {
            if (flashMode.getVisibility() == flashMode.VISIBLE)
                flashMode.setVisibility(ImageButton.GONE);
            else
                flashMode.setVisibility(ImageButton.VISIBLE);
        }
    }

    private void toggleFlash() {
        if(PermissionHandler.getFlashPermission(getActivity())) {
            mRenderer.toggleFlash();
            flashEnabled = !flashEnabled;
            if (flashEnabled) {
                flashMode.setBackgroundResource(R.drawable.flash_icon);
            } else {
                flashMode.setBackgroundResource(R.drawable.flash_icon_disabled);
            }
        }
    }


    private void switchSplitPosition() {
        if(splitPosition==0)        {
            mRenderer.setSplitPosition(1);
            splitPosition=1;
        }
        else{
            mRenderer.setSplitPosition(0);
            splitPosition=0;
        }
    }

    private void effectChange(boolean isChecked) {
        if (isChecked && !effectSet) {
            mRenderer.applyEffect(R.raw.vshader,R.raw.feffect);//send the appropriate f/v shader
            effectSet = true;
        }
        if (!isChecked&&effectSet) {
            mRenderer.removeEffect();
            effectSet=false;
        }
    }

    private void capture(){
        class CaptureTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected void onPreExecute() {
                capture.setVisibility(ImageButton.GONE);
                progressBar.setVisibility(ProgressBar.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                takePhoto();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBar.setVisibility(ProgressBar.GONE);
                capture.setVisibility(ImageButton.VISIBLE);
                Toast.makeText(getActivity().getApplicationContext(),"Saved Both the BMP and JPG",Toast.LENGTH_LONG).show();
                super.onPostExecute(aVoid);
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if(extPermissionGranted)
        new CaptureTask().execute();
        else
            return;
        }
        else
            new CaptureTask().execute();
    }

    @Override
    public void onStart(){
        super.onStart();

    }


    @Override
    public void onPause(){
        super.onPause();
        mRenderer.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
        mRenderer.onResume();
    }

    private void takePhoto(){
        if(PermissionHandler.getExtStoragePermission(getActivity()));
        {
            mRenderer.capture(currentOrientation);
        }
    }

    public void setOrientation(int orientation) {
        currentOrientation = (orientation%90)*90;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_FLASH: {
                if (grantResults.length == 0)
                    flashMode.setVisibility(ImageButton.GONE);
                }
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "App won't work without camera!!!!, BYE", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }
            case PERMISSIONS_REQUEST_EXTSD: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    extPermissionGranted = true;
                }
                else {
                    extPermissionGranted = false;
                }
            }
        }
    }
    private boolean isNewCameraVersion(){
        return (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(iCam ==null){
            iCam = (isNewCameraVersion() ? new CameraHandler() : new CameraHandler());
            mRenderer.setICamera(iCam);
        }

    }
}