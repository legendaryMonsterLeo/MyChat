package com.example.administrator.mychat.activity;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.mychat.R;
import com.example.administrator.mychat.util.ThreadUtils;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ThreadUtils.runInSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

}
