package com.example.administrator.mychat.util;

import android.os.Handler;

public class ThreadUtils {
    public static void runInSubThread(Runnable r){
        new Thread(r).start();
    }

    private static Handler handler = new Handler();

    public static void runInUiThread(Runnable r){
        handler.post(r);
    }
}
