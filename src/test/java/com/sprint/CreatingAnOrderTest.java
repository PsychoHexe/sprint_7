package com.sprint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
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

    public OrderCreateModel testOrder;
    public OrderCreateApi apiOrder;

    public CreatingAnOrderTest(OrderCreateModel testOrder) {
        this.testOrder = testOrder;
    }

    public List<OrderCreateModel> deleteOrderList = new ArrayList<>();

    @Parameterized.Parameters
    public static OrderCreateModel[] orders() {
        // Читаем из json данные для OrderTest
        final String pathCourierJson = "src/test/resources/orders.json";
        File jsonFile = new File(pathCourierJson);
        FileReader reader = new FileReader();
        String jsonText = reader.readToString(jsonFile, "UTF-8");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(jsonText, OrderCreateModel[].class);
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа ручка для создания /api/v1/orders")
    public void newOrderCreateTest(){        
        Response response = apiOrder.createOrder(testOrder);

        int trackId = trackCheckOrderCreate(response);

        testOrder.setTrack(trackId);

        deleteOrderList.add(testOrder);
    }

    @Step("Проверка ответа, что заказ был создан и получение трека")
    public int trackCheckOrderCreate(Response response) {
        int track = response.then().assertThat()
                .statusCode(201)
                .and().body(Matchers.containsString("track")).extract().path("track");
        return track;
    }

    @Before
    public void initApi(){
        apiOrder = new OrderCreateApi();
    }
    
    @After
    public void deleteOrder() {       
        
        
        if (deleteOrderList != null && !deleteOrderList.isEmpty())
        for (OrderCreateModel order : deleteOrderList) {            
            apiOrder.deleteOrder(order);
        }
        deleteOrderList.clear();
    }

}
