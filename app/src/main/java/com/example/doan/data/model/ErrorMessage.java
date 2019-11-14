package com.example.doan.data.model;

public class ErrorMessage {
    String localtion,param,msg,value;

    public ErrorMessage(String localtion, String param, String msg, String value) {
        this.localtion = localtion;
        this.param = param;
        this.msg = msg;
        this.value = value;
    }

    public String getLocaltion() {
        return localtion;
    }

    public String getParam() {
        return param;
    }

    public String getMsg() {
        return msg;
    }

    public String getValue() {
        return value;
    }
}
