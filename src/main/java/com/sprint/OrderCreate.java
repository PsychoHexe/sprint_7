package com.sprint;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class OrderCreate {

    private String firstName; // Имя
    private String lastName; // Фамилия
    private String address; // Адрес
    private String metroStation; // Станция метро
    private String phone; // Телефон
    private int rentTime; // Количество дней аренды
    private String Date; // Дата доставки
    private String comment; // Комментарий
    private String[] color; // Цвет

    // Конструктор со всеми полями
    public OrderCreate(String firstName, String lastName, String address, String metroStation,
            String phone, int rentTime, String deliveryDate, String comment, String[] color) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.Date = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    // Пустой конструктор
    public OrderCreate() {
    }

    // Геттеры для полей
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return Date;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColor() {
        return color;
    }

    // Сеттеры для полей
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.Date = deliveryDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    @Step("Отправляем запрос на создание заказа API")
    public Response createOrder() {
        return given()
                .header("Content-type", "application/json")
                .body(this)
                .when()
                .post(ListOfConstants.ORDERS);
    }

}
