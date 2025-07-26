package com.sprint;

import org.hamcrest.Matchers;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.sprint.data.CourierData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

public class CourierLoginTest extends CourierBaseTest {
    
    @Step
    private void checkLoginCourier(Response response) {
        response.then().assertThat().body(Matchers.containsString("id"))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Логин курьера (позитивный)")
    @Description("Курьер может залогиниться ручка для логина курьера/api/v1/courier/login")
    public void newCourierLoginPositiveTest() {
        CourierCreateApi api = new CourierCreateApi(CourierData.POSITIV_CREATE_LIST.get(0));

        Response response = api.createCourier();
        deleteCourierList.add(api.getModel());
        response = api.loginCourier();

        checkLoginCourier(response);

    }

    

    @Test
    @DisplayName("Логин курьера (негативный)")
    @Description("Курьер не может залогиниться, если данные не верны ручка для логина курьера/api/v1/courier/login")
    public void newCourierLoginNegativeTest() {

        CourierCreateModel courierCreate = CourierData.POSITIV_CREATE_LIST.get(0);
        CourierCreateApi api = new CourierCreateApi(courierCreate);

        Response response = api.createCourier();

        deleteCourierList.add(courierCreate);

        // Берем негативные случаи из списка
        for (CourierCreateModel courier : CourierData.NEGATIVE_LOGIN_LIST) {
            try {
                api.setModel(courier);
                response = api.loginCourier();

                int statusCode = response.then().extract().statusCode();

                switch (statusCode) {
                    case 400:
                        checkLoginNotEnter(response);
                        break;
                    case 404:
                        checkLoginNotFind(response);
                        break;
                    default:
                        String error = courier.getLogin() != courierCreate.getLogin() && !courier.getLogin().isEmpty()
                                ? "Возможно курьер уже был создан"
                                : "Вход с не валидными данными";
                        assertTrue(error, false);
                        break;
                }
            } catch (AssertionError e) {
                courierDataAssert(courier, " вход с не валидными данными");
                errorCollector.addError(e);
            }

        }
    }

    @Step("Учетная запись не найдена")
    private void checkLoginNotFind(Response response) {
        response.then().assertThat().body(Matchers.containsString("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    
    @Step("Не достаточно данных для входа")
    private void checkLoginNotEnter(Response response) {
        response.then().assertThat().body(Matchers.containsString("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

}
