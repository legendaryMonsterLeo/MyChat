package com.example.administrator.mychat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.mychat.R;
import com.example.administrator.mychat.adapter.ContactInfoAdapter;
import com.example.administrator.mychat.core.QQConnection;
import com.example.administrator.mychat.domain.ContactInfo;
import com.example.administrator.mychat.domain.ContactInfoList;
import com.example.administrator.mychat.domain.QQMessage;
import com.example.administrator.mychat.domain.QQMessageType;
import com.example.administrator.mychat.util.ThreadUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView;
    private List<ContactInfo> infos = new ArrayList<ContactInfo>();
    private ImApp app;
    private ContactInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addOnMessageListener();
        String buddyListJson = app.getBuddyListJson();
        System.out.println(buddyListJson);
        Gson gson = new Gson();
        ContactInfoList list = gson.fromJson(buddyListJson,ContactInfoList.class);
        infos.addAll(list.buddyList);
        adapter = new ContactInfoAdapter(getApplicationContext(),infos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   ContactInfo info = infos.get(position);
                   if(info.account!=app.getMyAccount()){
                       Intent intent = new Intent(getApplication(),ChartActivity.class);
                       intent.putExtra("account",info.account);
                       intent.putExtra("nick",info.nick);
                       startActivity(intent);
                   }else{
                       Toast.makeText(getBaseContext(),"不能和自己聊天",Toast.LENGTH_SHORT).show();
                   }
            }
        });
    }
    public void addOnMessageListener(){
        app = (ImApp) getApplication();
        app.getMyConn().addOnMessageListener(listener);
    }
    public void init(){
        listView = (ListView)findViewById(R.id.listview);
    }

    private QQConnection.OnMessageListener listener = new QQConnection.OnMessageListener() {
        @Override
        public void onReveive(final QQMessage msg) {
            ThreadUtils.runInUiThread(new Runnable() {
                @Override
                public void run() {
                    if(QQMessageType.MSG_TYPE_BUDDY_LIST.equals(msg.type)){
                        String newBuddyListJson = msg.content;
                        Gson gson = new Gson();
                        ContactInfoList newList = gson.fromJson(newBuddyListJson,ContactInfoList.class);
                        infos.clear();
                        infos.addAll(newList.buddyList);
                        if(adapter!=null){
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    };
}
