package com.example.administrator.mychat.activity;

import android.app.Application;

import com.example.administrator.mychat.core.QQConnection;

public class ImApp extends Application {
    private QQConnection myConn;
    private long myAccount;
    private String buddyListJson;

    public QQConnection getMyConn(){
        return myConn;
    }

    public void setMyConn(QQConnection myConn) {
        this.myConn = myConn;
    }

    public void setMyAccount(long myAccount) {
        this.myAccount = myAccount;
    }

    public long getMyAccount() {
        return myAccount;
    }

    public void setBuddyListJson(String buddyListJson) {
        this.buddyListJson = buddyListJson;
    }

    public String getBuddyListJson() {
        return buddyListJson;
    }
}
