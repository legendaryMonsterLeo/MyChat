package com.example.administrator.mychat.domain;

import com.thoughtworks.xstream.XStream;

public class ProtocalObj {

    public String toXML(){
        XStream x = new XStream();
        x.alias(getClass().getSimpleName(),getClass());
        String xml = x.toXML(this);
        return xml;
    }

    public Object fromXML(String xml){
        XStream x = new XStream();
        x.alias(getClass().getSimpleName(),getClass());
        Object object = x.fromXML(xml);
        return object;
    }
}
