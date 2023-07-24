package org.example.OrdersTests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Param;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
@Issue("3")
@Tag("create-order")
@DisplayName("3. Создание заказа")
public class CreateOrderTests {
    private final Map<String, String> testData;
    private final List<String> scooterColor;
    private Integer trackId;

    public CreateOrderTests(List<String> scooterColor) {
        testData = new HashMap<>();
        testData.put("firstName","testName");
        testData.put("lastName","testLastName");
        testData.put("address","Москва, Тестовая ул., д. 123");
        testData.put("metroStation","4");
        testData.put("phone","+7 (901) 234-56-78");
        testData.put("rentTime","3");
        testData.put("deliveryDate","2023-07-24");
        testData.put("comment","Some comment");

        this.scooterColor = scooterColor;

    }

    @Parameterized.Parameters(name = "Цвет самоката")
    public static Object[][] initParamsForTest() {
        return new Object[][] {
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @After
    public void clearAfterTests() {
        if (trackId == null) return;

        System.out.println(trackId);
        OrdersAPIHelper.deleteOrder(trackId);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Тест проверяет API создания заказа. Ожидаемый результат - заказ создан, возвращается его track-номер")
    public void createOrderIsSuccess() {
        Allure.parameter("Цвет самоката", scooterColor);

        Order order = new Order(
            testData.get("firstName"),
            testData.get("lastName"),
            testData.get("address"),
            testData.get("phone"),
            testData.get("rentTime"),
            testData.get("deliveryDate"),
            testData.get("comment"),
            scooterColor
        );

        Response response = OrdersAPIHelper.createOrder(order);
        response.then().statusCode(201).and().assertThat().body("track", notNullValue());

        this.trackId = response.body().as(OrderTrackId.class).getTrack();
    }

}
