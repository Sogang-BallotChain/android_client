package com.example.navdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.navdrawer.R;

public class SplashActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.e("bsos1202","SplashActivity income!!");
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() { @Override public void run() { finish(); } }, 3000);
        // 3초 후 이미지를 닫습니다
    }
}