package com.sprint_7;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class CourierCreate {

    private String login;
    private String password;
    private String firstName;
    private int id;

    // конструктор класса
    public CourierCreate(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // пустой конструктор
    public CourierCreate() {

    }

    // Геттеры для полей
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

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

    // 1. Создание нового курьера
    @Step("Отправляем запрос на создание курьера API")
    public Response createCourier() {
        return given()
                .header("Content-type", "application/json")
                .body(this)
                .when()
                .post(ListOfConstants.COURIER);
    }

    // 2. Логин курьера
    @Step("Логинимся курьером")
    public Response loginCourier() {
        return given()
                .header("Content-type", "application/json")
                .body("{\"login\":\"" + this.login + "\",\"password\":\"" + this.password + "\"}")
                .when()
                .post(ListOfConstants.COURIER_LOGIN);
    }

    // 3. Получение ID курьера
    @Step("Запрашиваем ID курьера")
    public int getCourierId() {
        Response response = loginCourier();
        this.id = response.then().extract().path("id"); // Сохраняем ID в объект
        return this.id;
    }

    // 4. Удаление курьера
    @Step("Удаляем курьра")
    public Response deleteCourier() {
        int courierId = getCourierId();
        return given()
                .when()
                .delete(ListOfConstants.COURIER + "/" + courierId);
    }

}
