package com.example.administrator.mychat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mychat.R;
import com.example.administrator.mychat.activity.ImApp;
import com.example.administrator.mychat.domain.QQMessage;

import java.util.List;

public class ChartMessageAdapter extends ArrayAdapter<QQMessage> {
    ImApp app;
    public ChartMessageAdapter(Context context, List<QQMessage>objects){
        super(context,0,objects);
        Activity activity = (Activity) context;
        app = (ImApp)activity.getApplication();
    }
    //分清发送和收到的消息，我真的太机智了吧
    public int getItemViewType(int position){
        QQMessage msg = getItem(position);
        if(msg.from == app.getMyAccount()){
            return 0;
        }else {
            return 1;
        }
    }
    public int getViewTypeCount(){
        return 2;
    }
    class ViewHolder{
        TextView time;
        TextView content;
        ImageView head;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        int type = getItemViewType(position);
        if(0==type){
            //发送布局
            ViewHolder holder;
            if(convertView == null){
                convertView = View.inflate(getContext(), R.layout.item_chat_send,null);
                holder = new ViewHolder();
                holder.time = (TextView)convertView.findViewById(R.id.time);
                holder.content=(TextView)convertView.findViewById(R.id.content);
                holder.head = (ImageView)convertView.findViewById(R.id.head);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            QQMessage msg = getItem(position);
            holder.time.setText(msg.sendTime);
            holder.head.setImageResource(msg.fromAvatar);
            holder.content.setText(msg.content);
            return convertView;
        }else {
            //接收布局
            ViewHolder holder;
            if(convertView == null){
                convertView = View.inflate(getContext(), R.layout.item_chat_receive,null);
                holder = new ViewHolder();
                holder.time = (TextView)convertView.findViewById(R.id.time);
                holder.content=(TextView)convertView.findViewById(R.id.content);
                holder.head = (ImageView)convertView.findViewById(R.id.head);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            QQMessage msg = getItem(position);
            holder.time.setText(msg.sendTime);
            holder.head.setImageResource(msg.fromAvatar);
            holder.content.setText(msg.content);
            return convertView;
        }
    }
}
