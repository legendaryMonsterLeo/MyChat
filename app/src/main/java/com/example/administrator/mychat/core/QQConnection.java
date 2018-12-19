package com.example.administrator.mychat.core;

import com.example.administrator.mychat.domain.QQMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QQConnection {

    private String host = "";
    private int port = 8090;
    Socket client;
    private DataInputStream reader;
    private DataOutputStream writer;
    private WaitThread waitThread;
    public boolean isWaiting;

    public QQConnection(String host,int port){
        super();
        this.host = host;
        this.port = port;
    }
   public void connect()throws UnknownHostException,IOException{
        client = new Socket(host,port);
        writer = new DataOutputStream(client.getOutputStream());
        reader = new DataInputStream(client.getInputStream());
        isWaiting = true;
        waitThread = new WaitThread();
        waitThread.start();
   }
   public void disConnet()throws IOException{
        client.close();
        writer.close();
        reader.close();
        isWaiting = false;
   }
   public void sendMessage(String xml)throws IOException{
        writer.writeUTF(xml);
   }
   public void sendMessage(QQMessage msg)throws IOException{
        writer.writeUTF(msg.toXML());
   }
    private class WaitThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(isWaiting){
                try{
                    String xml = reader.readUTF();
                    QQMessage msg = new QQMessage();
                    msg = (QQMessage)msg.fromXML(xml);
                    for(OnMessageListener listener : listeners){
                        listener.onReveive(msg);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private List<OnMessageListener> listeners = new ArrayList<OnMessageListener>();
    public void addOnMessageListener (OnMessageListener msg){
        listeners.add(msg);
    }
    public void removeOnMessageListener(OnMessageListener msg){
        listeners.remove(msg);
    }
    public interface OnMessageListener{
        public void onReveive(QQMessage msg);
    }
}
