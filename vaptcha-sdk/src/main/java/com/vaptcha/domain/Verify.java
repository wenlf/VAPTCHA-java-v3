package com.vaptcha.domain;

public class Verify {
    private String token;
    private String id;
    private String secretkey;
    private String scene;

    @Override
    public String toString() {
        return "Verify{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", secretkey='" + secretkey + '\'' +
                ", scene='" + scene + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public Verify() {
    }

    public Verify(String token, String id, String secretkey, String scene) {
        this.token = token;
        this.id = id;
        this.secretkey = secretkey;
        this.scene = scene;
    }
}
