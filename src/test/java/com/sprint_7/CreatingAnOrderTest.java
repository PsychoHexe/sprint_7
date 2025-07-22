package com.sprint_7;

import java.io.File;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.internal.support.FileReader;
import io.restassured.response.Response;

@RunWith(Parameterized.class)
public class CreatingAnOrderTest extends BaseTest{

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    public OrderCreate testOrder;

    public CreatingAnOrderTest(OrderCreate testOrder) {
this.testOrder = testOrder;
    }

    @Parameterized.Parameters
    public static OrderCreate[] orders() {
        // Читаем из json данные для OrderTest
        final String pathCourierJson = "src/test/resources/orders.json";
        File jsonFile = new File(pathCourierJson);
        FileReader reader = new FileReader();
        String jsonText = reader.readToString(jsonFile, "UTF-8");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(jsonText, OrderCreate[].class);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа ручка для создания /api/v1/orders")
    public void newOrderCreateTest(){        

        Response response = testOrder.createOrder();
        checkOrderCreate(response);
    }

    @Step
    public void checkOrderCreate(Response response) {
        response.then().assertThat().body(Matchers.containsString("track"))
                .and()
                .statusCode(201);
    }

}
