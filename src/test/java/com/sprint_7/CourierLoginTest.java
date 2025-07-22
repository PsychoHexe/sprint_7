package com.sprint_7;

import org.hamcrest.Matchers;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import com.sprint_7.data.CourierData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

public class CourierLoginTest extends CourierBaseTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Step
    private void checkLoginCourier(Response response) {
        response.then().assertThat().body(Matchers.containsString("id"))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер может залогиниться ручка для логина курьера/api/v1/courier/login")
    public void newCourierLoginPositiveTest() {
        CourierCreate courierCreate = CourierData.POSITIV_CREATE_LIST.get(0);

        Response response = courierCreate.createCourier();
        deleteCourierList.add(courierCreate);
        response = courierCreate.loginCourier();

        checkLoginCourier(response);

    }

    

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер не может залогиниться, если данные не верны ручка для логина курьера/api/v1/courier/login")
    public void newCourierLoginNegativeTest() {
        // Берем негативные случаи из списка
        CourierCreate courierCreate = CourierData.POSITIV_CREATE_LIST.get(0);

        Response response = courierCreate.createCourier();
        deleteCourierList.add(courierCreate);

        for (CourierCreate courier : CourierData.NEGATIVE_LOGIN_LIST) {
            
            response = courier.loginCourier();

            int statusCode = response.then().extract().statusCode();

            switch (statusCode) {
                case 400:
                    response.then().assertThat().body(Matchers.containsString("Недостаточно данных для входа"))
                            .and()
                            .statusCode(400);
                    break;
                case 404:
                    response.then().assertThat().body(Matchers.containsString("Учетная запись не найдена"))
                            .and()
                            .statusCode(404);
                    break;
                default:
                    assertTrue("", false);
                    break;
            }

        }
    }

}
