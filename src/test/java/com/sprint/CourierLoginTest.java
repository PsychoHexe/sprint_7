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
    @DisplayName("Логин курьера")
    @Description("Курьер может залогиниться ручка для логина курьера/api/v1/courier/login")
    public void newCourierLoginPositiveTest() {
        CourierCreateApi api = new CourierCreateApi(CourierData.POSITIV_CREATE_LIST.get(0));

        Response response = api.createCourier();
        deleteCourierList.add(api.getModel());
        response = api.loginCourier();

        checkLoginCourier(response);

    }

    

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер не может залогиниться, если данные не верны ручка для логина курьера/api/v1/courier/login")
    public void newCourierLoginNegativeTest() {
        // Берем негативные случаи из списка
        CourierCreateModel courierCreate = CourierData.POSITIV_CREATE_LIST.get(0);
        CourierCreateApi api = new CourierCreateApi(courierCreate);

        Response response = api.createCourier();
        deleteCourierList.add(courierCreate);        
        
        for (CourierCreateModel courier : CourierData.NEGATIVE_LOGIN_LIST) {
            
            api.setModel(courier);
            response =  api.loginCourier();

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
