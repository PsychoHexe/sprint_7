/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package com.sprint_7;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import com.sprint_7.data.CourierData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

public class CourierCreateTest extends CourierBaseTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();
    
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
        CourierCreate courierCreate = CourierData.POSITIV_CREATE_LIST.get(0);
        Response response = courierCreate.createCourier();
        stepCheckUserCreate(response);

        deleteCourierList.add(courierCreate);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("ручка для создания курьера/api/v1/courier с негативными данными")
    public void newCourierCreateNegativeTest() {
        
        for (CourierCreate courier : CourierData.NEGATIVE_CREATE_LIST) {

            Response respons = courier.createCourier();
            // Чтобы очистить всех пользователей добавляем его в список на удаления
            if (respons.statusCode() == 201) {
                int courierId = courier.getCourierId();
                deleteCourierList.add(courier);
            }

            checkCreateNegative(respons);
        }
    }

    

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание с существующим логином ручка для создания курьера/api/v1/courier")
    public void newCourierCreateSameTest() {

        CourierCreate courierCreate1 = CourierData.POSITIV_CREATE_LIST.get(0);
        Response response1 = courierCreate1.createCourier();

        deleteCourierList.add(courierCreate1);
        
        CourierCreate courierCreate2 = CourierData.POSITIV_CREATE_LIST.get(1);
        Response response2 = courierCreate2.createCourier();
        
        stepCheckDubleSame(response2);

    }
   

}