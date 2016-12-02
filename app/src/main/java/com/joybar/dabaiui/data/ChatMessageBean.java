package com.joybar.dabaiui.data;

/**
 * Created by joybar on 02/12/16.
 */
public class ChatMessageBean {

    /**
     * 消息类型
     */
    private Type type;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 日期
     */

    public ChatMessageBean() {
    }

    public ChatMessageBean(Type type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public enum Type {
        SEND, RECEIVE
    }
}
