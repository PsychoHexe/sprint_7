package com.sprint;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class CourierCreateApi {

    
    // 1. Создание нового курьера
    @Step("Отправляем запрос на создание курьера API")
    public Response createCourier( CourierCreateModel model ) {
        return given()
                .header("Content-type", "application/json")
                .body(model)
                .when()
                .post(ListOfConstants.COURIER);
    }

    // 2. Логин курьера
    @Step("Логинимся курьером")
    public Response loginCourier( CourierCreateModel model ) {
        return given()
                .header("Content-type", "application/json")
                .body(model.toLoginModel())
                .when()
                .post(ListOfConstants.COURIER_LOGIN);
    }

    // 3. Получение ID курьера
    @Step("Запрашиваем ID курьера")
    public int getCourierId( CourierCreateModel model ) {
        Response response = loginCourier(model);
        model.setId(response.then().extract().path("id")); // Сохраняем ID в объект
        return model.getId();
    }

    // 4. Удаление курьера
    @Step("Удаляем курьра")
    public Response deleteCourier(CourierCreateModel model) {
        int courierId = getCourierId(model);
        return given()
                .when()
                .delete(ListOfConstants.COURIER + "/" + courierId);
    }
}
