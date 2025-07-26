package com.sprint;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class OrderCreateApi {
    private OrderCreateModel order;
    public int id;

    public OrderCreateModel getOrder() {
        return order;
    }

    public void setOrder(OrderCreateModel order) {
        this.order = order;
    }

    @Step("Отправляем запрос на создание заказа API")
    public Response createOrder() {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ListOfConstants.ORDERS);
    }

    @Step("Отменяем заказ")
    public Response deleteOrder() {
        return given()
                .header("Content-type", "application/json")
                .body("{\"track\": " + order.getTrack() + "}")
                .when()
                .put(ListOfConstants.ORDERS_CANCEL);
    }

    @Step("Получить список заказа без указания полей")
    public Response getOrderList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ListOfConstants.ORDERS);
    }

    @Step("Получить список заказа c указанием id курьера")
    public Response getOrderListId() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ListOfConstants.ORDERS + "?courierId=" + id);
    }

    @Step("Получить список заказа c указанием лимита заказов на странице")
    public Response getOrderLimitList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ListOfConstants.ORDERS + "?limit=10");
    }

    @Step("Получить список заказа c указанием станции метро")
    public Response getOrderNearestStationList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ListOfConstants.ORDERS + "?nearestStation=[\"5\"]");
    }
}