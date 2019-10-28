package com.vaptcha.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ImgResp {
    private String code;
    @SerializedName("imgid")
    private String imgId;
    private String knock;
    private String msg;
    private String key;

    public ImgResp(String code, String imgId, String knock, String msg, String key) {
        this.code = code;
        this.imgId = imgId;
        this.knock = knock;
        this.msg = msg;
        this.key = key;
    }

    public ImgResp() {
    }

    @Override
    public String toString() {
        return "ImgResp{" +
                "code='" + code + '\'' +
                ", imgId='" + imgId + '\'' +
                ", knock='" + knock + '\'' +
                ", msg='" + msg + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getKnock() {
        return knock;
    }

    public void setKnock(String knock) {
        this.knock = knock;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
