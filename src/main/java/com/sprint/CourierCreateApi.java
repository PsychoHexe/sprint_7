package com.sprint;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class CourierCreateApi {

    private CourierCreateModel model;

    private int getId() {
        return model.getId();
    }

    public CourierCreateModel getModel(){return model;}
    public void setModel(CourierCreateModel model) {
        this.model = model;
    }

    CourierCreateApi(){}
    CourierCreateApi(CourierCreateModel model){
        this.model = model;
    }

    // 1. Создание нового курьера
    @Step("Отправляем запрос на создание курьера API")
    public Response createCourier() {
        return given()
                .header("Content-type", "application/json")
                .body(model)
                .when()
                .post(ListOfConstants.COURIER);
    }

    // 2. Логин курьера
    @Step("Логинимся курьером")
    public Response loginCourier() {
        return given()
                .header("Content-type", "application/json")
                .body(model.toLoginModel())
                .when()
                .post(ListOfConstants.COURIER_LOGIN);
    }

    // 3. Получение ID курьера
    @Step("Запрашиваем ID курьера")
    public int getCourierId() {
        Response response = loginCourier();
        model.setId(response.then().extract().path("id")); // Сохраняем ID в объект
        return model.getId();
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
