/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package com.sprint;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.gson.Gson;
import com.sprint.data.CourierData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

public class CourierCreateTest extends CourierBaseTest {
        
    @Step("Проверка пользователь создан")
    public void stepCheckUserCreate(Response response) {
        response.then().assertThat().statusCode(201).and().extract().path("ok");
    }

    @Step("Проверка создания курьеров с одинаковым логином")
    private void stepCheckDubleSame(Response response) {
        response.then().assertThat()
                .statusCode(409)
                .and().body(Matchers.containsString("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Проверка на ответ при создании с невалидными данными")
    private void checkCreateNegative(Response respons) {
        respons.then().assertThat()
                .statusCode(400)
                .and().body(Matchers.containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера (позитивный тест)")
    @Description("ручка для создания курьера/api/v1/courier")
    public void newCourierCreatePositiveTest() {
        CourierCreateModel courierCreate = CourierData.POSITIV_CREATE_LIST.get(0);

        courierCreate.getLogin();
        
        Response response = api.createCourier(courierCreate);

        stepCheckUserCreate(response);

        deleteCourierList.add(courierCreate);
    }

    @Test
    @DisplayName("Создание курьера (негативный тест)")
    @Description("ручка для создания курьера/api/v1/courier с негативными данными")
    public void newCourierCreateNegativeTest() {
        
        Gson gson = new Gson();
        for (CourierCreateModel courier : CourierData.NEGATIVE_CREATE_LIST) {

            Response respons = api.createCourier(courier);
            
            // Чтобы очистить всех пользователей добавляем его в список на удаления
            if (respons.statusCode() == 201) {
                api.getCourierId(courier);
                deleteCourierList.add(courier);
            }
            try {
                checkCreateNegative(respons);
            } catch (AssertionError e) {
                System.err.println(gson.toJson(courier));
                errorCollector.addError(e);
                courierDataAssert(courier, " создан с не валидными данными");
            }
        }
    }

    

    @Test
    @DisplayName("Создание курьера (повторно не создается)")
    @Description("Создание с существующим логином ручка для создания курьера/api/v1/courier")
    public void newCourierCreateSameTest() {

        CourierCreateModel courierCreate1 = CourierData.POSITIV_CREATE_LIST.get(0);
        
        Response response1 = api.createCourier(courierCreate1);

        deleteCourierList.add(courierCreate1);
        
        CourierCreateModel courierCreate2 = CourierData.POSITIV_CREATE_LIST.get(1);
        
        Response response2 = api.createCourier(courierCreate2);
        
        stepCheckDubleSame(response2);

    }
   

}