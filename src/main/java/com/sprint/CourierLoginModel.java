package com.sprint;

public class CourierLoginModel {
    protected String login;
    protected String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // конструктор класса
    public CourierLoginModel() {
    }

    public CourierLoginModel(String login, String password) {
        this.login = login;
        this.password = password;
    }
}