package com.sunny.dto.vo;

public class LoginVO extends UserVO {
    private String token;

    @Override
    public String toString() {
        return "LoginVO{" +
                "token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
