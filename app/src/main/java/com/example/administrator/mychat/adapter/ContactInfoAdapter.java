package com.example.administrator.mychat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.mychat.R;

import com.example.administrator.mychat.domain.ContactInfo;

import java.util.List;

public class ContactInfoAdapter extends ArrayAdapter<ContactInfo> {
    public ContactInfoAdapter(Context context, List<ContactInfo> objects){
        super(context,0,objects);
    }
    class ViewHolder{
        ImageView icon;
        TextView title;
        TextView desc;
    }
    public View getView(int postion, View converView, ViewGroup parent){
        ContactInfo info = getItem(postion);
        ViewHolder holder;
        if(converView==null){
            converView = View.inflate(getContext(),R.layout.view_item_contact,null);
            holder = new ViewHolder();
            holder.icon = (ImageView)converView.findViewById(R.id.icon);
            holder.title = (TextView)converView.findViewById(R.id.title);
            holder.desc = (TextView)converView.findViewById(R.id.desc);
            converView.setTag(holder);
        }else{
            holder = (ViewHolder)converView.getTag();
        }
        if(info.avatar==0){
            holder.icon.setImageResource(R.drawable.touxiang1);
        }else{
            holder.icon.setImageResource(R.drawable.touxiang2);
        }

        holder.title.setText(info.account+"");
        holder.desc.setText(info.nick);
        return converView;
    }

}
