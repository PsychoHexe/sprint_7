/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package com.sprint;

import org.hamcrest.Matchers;
import org.junit.Test;

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
        response.then().assertThat().body(Matchers.containsString("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Step("Проверка на ответ при создании с невалидными данными")
    private void checkCreateNegative(Response respons) {
        respons.then().assertThat().body(Matchers.containsString("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("ручка для создания курьера/api/v1/courier")
    public void newCourierCreatePositiveTest() {
        CourierCreateModel courierCreate = CourierData.POSITIV_CREATE_LIST.get(0);

        courierCreate.getLogin();

        CourierCreateApi api = new CourierCreateApi(courierCreate);
        Response response = api.createCourier();
        stepCheckUserCreate(response);

        deleteCourierList.add(courierCreate);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("ручка для создания курьера/api/v1/courier с негативными данными")
    public void newCourierCreateNegativeTest() {
        
        CourierCreateApi api = new CourierCreateApi();        

        for (CourierCreateModel courier : CourierData.NEGATIVE_CREATE_LIST) {
            api.setModel(courier);
            Response respons = api.createCourier();
            // Чтобы очистить всех пользователей добавляем его в список на удаления
            if (respons.statusCode() == 201) {
                int courierId = api.getCourierId();
                deleteCourierList.add(courier);
            }

            checkCreateNegative(respons);
        }
    }

    

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание с существующим логином ручка для создания курьера/api/v1/courier")
    public void newCourierCreateSameTest() {

        CourierCreateModel courierCreate1 = CourierData.POSITIV_CREATE_LIST.get(0);
        CourierCreateApi api1 = new CourierCreateApi(courierCreate1);
        Response response1 = api1.createCourier();

        deleteCourierList.add(courierCreate1);
        
        CourierCreateModel courierCreate2 = CourierData.POSITIV_CREATE_LIST.get(1);
        CourierCreateApi api2 = new CourierCreateApi(courierCreate2);
        Response response2 = api2.createCourier();
        
        stepCheckDubleSame(response2);

    }
   

}