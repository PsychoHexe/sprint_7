
package com.sprint;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

public class OrderListTest extends BaseTest {

    public OrderCreateApi apiOrder;

    public OrderListTest() {
        this.apiOrder = new OrderCreateApi();
    }

    @Test
    @DisplayName("Получение списка заказов без указания даных")
    @Description("Ручка для получения списка /api/v1/orders")
    public void receivingOrderTest() {

        Response response = apiOrder.getOrderList();
        checkOrderList(response);

    }

    @Test
    @DisplayName("Получение списка заказов c лимитом 10 заказов на странице")
    @Description("Ручка для получения списка /api/v1/orders")
    public void receivingOrderLimitTest() {

        Response response = apiOrder.getOrderLimitList();
        checkOrderList(response);

    }

    @Test
    @DisplayName("Получение списка заказов при указании определенной станции")
    @Description("Ручка для получения списка /api/v1/orders")
    public void receivingOrderNearestStationTest() {

        Response response = apiOrder.getOrderNearestStationList();
        checkOrderList(response);

    }

    @Step("Проверка ответа, что заказы были найдены")
    public void checkOrderList(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .and().body(Matchers.containsString("orders"));

    }
}