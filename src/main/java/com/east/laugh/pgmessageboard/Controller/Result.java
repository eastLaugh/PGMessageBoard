package com.east.laugh.pgmessageboard.Controller;

public class Result {
    Object data;
    boolean success;
    String msg;

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(Object data) {
        this.data = data;
        this.success = true;
    }

    public Object getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
