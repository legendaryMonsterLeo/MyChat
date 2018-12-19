package com.example.administrator.mychat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mychat.R;
import com.example.administrator.mychat.adapter.ChartMessageAdapter;
import com.example.administrator.mychat.core.QQConnection;
import com.example.administrator.mychat.core.QQConnection.OnMessageListener;
import com.example.administrator.mychat.domain.QQMessage;
import com.example.administrator.mychat.domain.QQMessageType;
import com.example.administrator.mychat.util.ThreadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends Activity implements View.OnClickListener {

    private TextView title;
    private ListView listView;
    private EditText input;
    private Button send;
    private ImApp app;
    private ChartMessageAdapter adapter;
    private String toNick;
    private long toAccount;
    private long fromAccount;
    private String inputStr;
    private List<QQMessage>messages = new ArrayList<QQMessage>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        init();
        send.setOnClickListener(this);
        app = (ImApp)getApplication();
        app.getMyConn().addOnMessageListener(listener);
        Intent intent = getIntent();
        toNick=intent.getStringExtra("nick");
        toAccount=intent.getLongExtra("account",0);
        title.setText("与"+toNick+"聊天中");
        fromAccount = app.getMyAccount();
        adapter = new ChartMessageAdapter(this,messages);
        listView.setAdapter(adapter);
    }
    protected void onDestroy(){
        super.onDestroy();
        app.getMyConn().removeOnMessageListener(listener);
    }
    public void init(){
        title = findViewById(R.id.title);
        listView = findViewById(R.id.listview);
        input = findViewById(R.id.input);
        send = findViewById(R.id.send);
    }

    @Override
    public void onClick(View v) {
        inputStr = input.getText().toString().trim();
        input.setText("");
        final  QQMessage msg = new QQMessage();
        if("".equals(inputStr)){
            Toast.makeText(getApplicationContext(),"不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        msg.type = QQMessageType.MSG_TYPE_CHAT_P2P;
        msg.from = fromAccount;
        msg.to=toAccount;
        msg.content = inputStr;
        msg.fromAvatar = R.drawable.touxiang1;
         messages.add(msg);

         if(adapter!=null){
             adapter.notifyDataSetChanged();
         }

         if(messages.size()>0){
             listView.setSelection(messages.size()-1);
         }
        ThreadUtils.runInSubThread(new Runnable() {
            @Override
            public void run() {
                try{
                    QQConnection connection = app.getMyConn();
                    connection.sendMessage(msg.toXML());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
    private OnMessageListener listener = new OnMessageListener() {
        @Override
        public void onReveive(final QQMessage msg) {
              ThreadUtils.runInUiThread(new Runnable() {
                  @Override
                  public void run() {
                      System.out.println(msg.content);
                      if(QQMessageType.MSG_TYPE_CHAT_P2P.equals(msg.type)){
                          messages.add(msg);
                          if(adapter!=null){
                              adapter.notifyDataSetChanged();
                          }
                          if(messages.size()>0){
                              listView.setSelection(messages.size()-1);
                          }
                      }
                  }
              });
        }
    };
}
