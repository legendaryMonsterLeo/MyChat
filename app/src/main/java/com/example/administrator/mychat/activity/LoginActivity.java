package com.example.administrator.mychat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mychat.R;
import com.example.administrator.mychat.core.QQConnection;
import com.example.administrator.mychat.domain.QQMessage;
import com.example.administrator.mychat.domain.QQMessageType;
import com.example.administrator.mychat.util.ThreadUtils;

import java.io.IOException;


public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText account;
    private EditText password;
    private Button login;
    private String accountStr;
    private String passwordStr;
    QQConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        login.setOnClickListener(this);
        ThreadUtils.runInSubThread(new Runnable() {   //子线程网络通讯
            @Override
            public void run() {
                try{
                    conn = new QQConnection("192.168.56.1",8090);
                    conn.connect();
                    conn.addOnMessageListener(listener);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
        conn.removeOnMessageListener(listener);
    }

    @Override
    public void onClick(View v) {
            accountStr = account.getText().toString().trim();
            passwordStr = password.getText().toString().trim();

            ThreadUtils.runInSubThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        QQMessage msg = new QQMessage();
                        msg.type = QQMessageType.MSG_TYPE_LOGIN;
                        msg.content = accountStr + "#"+passwordStr;
                        conn.sendMessage(msg);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
    }

    public void init() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
    }

    private QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {
        @Override
        public void onReveive(final QQMessage msg) {
            System.out.println(msg.toXML());

            ThreadUtils.runInUiThread(new Runnable() {
                @Override
                public void run() {
                    if(QQMessageType.MSG_TYPE_BUDDY_LIST.equals(msg.type)){
                        Toast.makeText(getBaseContext(),"登陆成功",Toast.LENGTH_LONG).show();
                        ImApp app = (ImApp) getApplication();
                        app.setMyConn(conn);
                        app.setBuddyListJson(msg.content);
                        app.setMyAccount(Long.parseLong(accountStr));
                        Intent intent = new Intent();
                        intent.setClass(getBaseContext(),MainActivity.class);
                        intent.putExtra("account",accountStr);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getBaseContext(),"登陆失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

}
