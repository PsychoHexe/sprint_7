package com.sprint;

import org.hamcrest.Matchers;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.sprint.data.CourierData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

public class CourierLoginTest extends CourierBaseTest {

    CourierCreateModel courier;

    @Before
    public void courierInitPositive(){
        courier = CourierData.POSITIV_CREATE_LIST.get(0);
        //Добавляем курьера в список на удаление в after в методе CourierBaseTest.deleteCouriers()
        deleteCourierList.add(courier);
    }

    @Step("Проверка курьера, id получен")
    private void checkLoginCourier(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .and().body(Matchers.containsString("id"));
    }

    @Test
    @DisplayName("Логин курьера (позитивный)")
    @Description("Курьер может залогиниться ручка для логина курьера/api/v1/courier/login")
    public void newCourierLoginPositiveTest() {
        
        Response response = api.createCourier(courier);
        
        response = api.loginCourier(courier);
        
        checkLoginCourier(response);
        
    }

    private void negativeLogin(CourierCreateModel courierNegative) {

        Response response = api.createCourier(this.courier);

        try {
            response = api.loginCourier(courierNegative);

            int statusCode = response.then().extract().statusCode();

            switch (statusCode) {
                case 400:
                    checkLoginNotEnter(response);
                    break;
                case 404:
                    checkLoginNotFind(response);
                    break;
                default:
                    String error = courierNegative.getLogin() != courierNegative.getLogin()
                            && !courierNegative.getLogin().isEmpty()
                                    ? "Возможно курьер уже был создан"
                                    : "Вход с не валидными данными";
                    assertTrue(error, false);
                    break;
            }
        } catch (AssertionError e) {
            courierDataAssert(courierNegative, " вход с не валидными данными");
            errorCollector.addError(e);
        }
    }

    @Test
    @DisplayName("Логин курьера (пустой логин)")
    @Description("Курьер не может залогиниться, если данные не верны ручка для логина курьера/api/v1/courier/login")
    public void negativeLoginVoidTest(){
        negativeLogin(CourierData.NEGATIVE_LOGIN_LIST.get(0));
    }

    @Test
    @DisplayName("Логин курьера (пустой пароль)")
    @Description("Курьер не может залогиниться, если данные не верны ручка для логина курьера/api/v1/courier/login")
    public void negativePassVoidTest(){
        negativeLogin(CourierData.NEGATIVE_LOGIN_LIST.get(1));
    }

    @Test
    @DisplayName("Логин курьера (не правильный логин)")
    @Description("Курьер не может залогиниться, если данные не верны ручка для логина курьера/api/v1/courier/login")
    public void negativeLoginVrongTest(){
        negativeLogin(CourierData.NEGATIVE_LOGIN_LIST.get(2));
    }

    @Test
    @DisplayName("Логин курьера (не правильный пароль)")
    @Description("Курьер не может залогиниться, если данные не верны ручка для логина курьера/api/v1/courier/login")
    public void negativePassVrongTest(){
        negativeLogin(CourierData.NEGATIVE_LOGIN_LIST.get(3));
    }

    @Step("Учетная запись не найдена")
    private void checkLoginNotFind(Response response) {
        response.then().assertThat()
                .statusCode(404)
                .and().body(Matchers.containsString("Учетная запись не найдена"));
    }

    @Step("Не достаточно данных для входа")
    private void checkLoginNotEnter(Response response) {
        response.then().assertThat()
                .statusCode(400)
                .and().body(Matchers.containsString("Недостаточно данных для входа"));
    }

}
