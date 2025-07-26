package com.sprint;

public class CourierCreateModel {
    private String login;
    private String password;
    private String firstName;
    private int id;

    // конструктор класса
    public CourierCreateModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // пустой конструктор
    public CourierCreateModel() {

    }

    CourierLoginModel toLoginModel(){
        return new CourierLoginModel(login, password);
    }
    
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // Геттеры для полей

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    // Сеттеры для полей
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}
