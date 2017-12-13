package com.cncustompoc.SingletonSrvcs.domains;

public class APITokenReply {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    @Override
    public String toString() {
        return "APITokenReply{" +
                "token='" + token + '\'' +
                '}';
    }
}
