package com.example.kharcha.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kharcha.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2000)
                .withLogo(R.mipmap.ic_launcher)
                .withFooterText("© sl")
                .withBackgroundResource(R.color.white);
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
