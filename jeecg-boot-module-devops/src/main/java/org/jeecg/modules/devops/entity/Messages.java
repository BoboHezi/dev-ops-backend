package org.jeecg.modules.devops.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Messages<DATA> {
    private int code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DATA data;

    public static Messages Success(){
        return new Messages();
    }

    public static Messages Error(){
        int code= Resoure.Code.FAIL;
        String msg=Resoure.Message.get(code);
        return new Messages(code, msg);
    }

    public static Messages Error(String content){
        int code= Resoure.Code.FAIL;
        String msg=Resoure.Message.get(code);
        return new Messages<String>(code, msg, content);
    }

    public Messages(){
        this.code= Resoure.Code.SUCCESS;
        this.msg=Resoure.Message.get(code);
    }

    public Messages(int code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public Messages(int code, String msg, DATA t){
        this.code=code;
        this.msg=msg;
        this.data = t;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setT(DATA t) {
        this.data = t;
    }

    public DATA getDATA() {
        return data;
    }

    @Override
    public String toString() {
        if(data==null){
            return "Messages{" +
                    "code=" + code +
                    ", msg='" + msg;
        }
        else {
            return "Messages{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data.toString() +
                    '}';
        }
    }
}
