package com.g.mike.glcamera;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentController;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.g.mike.glcamera.gl.CameraRenderer;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        Fragment fragment  = new DisplayFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main,fragment);
        ft.commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
