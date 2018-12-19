package com.example.administrator.mychat.domain;

import com.example.administrator.mychat.util.MyTime;

public class QQMessage extends ProtocalObj{
    public String type = QQMessageType.MSG_TYPE_CHAT_P2P;
    public long from = 0;
    public String fromNick = "";
    public int fromAvatar = 1;
    public long to = 0;
    public String content = "";
    public String sendTime = MyTime.getTime();
}
