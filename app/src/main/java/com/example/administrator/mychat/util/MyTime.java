package com.example.administrator.mychat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {
    public static String getTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        return format.format(date);
    }
    public static String getTime(Long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        return format.format(date);
    }
}
