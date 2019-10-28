package com.vaptcha.domain;

public class SecondVerifyResp {
    private int code;
    private String msg;
    private int score;

    @Override
    public String toString() {
        return "SecondVerifyResp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", score=" + score +
                '}';
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SecondVerifyResp(int code, String msg, int score) {
        this.code = code;
        this.msg = msg;
        this.score = score;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SecondVerifyResp() {
    }

    public SecondVerifyResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
