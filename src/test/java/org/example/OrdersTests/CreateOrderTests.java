package org.example.OrdersTests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CreateOrder", name = "#api-Orders-CreateOrder")
@Tag("create-order")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API создания заказа")
@DisplayName("3. Создание заказа")
public class CreateOrderTests extends OrdersAPITests {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phone;
    private final String rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> scooterColor;
    private Integer trackId;

    public CreateOrderTests(List<String> scooterColor) {
        firstName = "testName";
        lastName = "testLastName";
        address = "Москва, Тестовая ул., д. 123";
        phone = "+7 (901) 234-56-78";
        rentTime = "3";
        deliveryDate = "2023-07-24";
        comment = "Some comment";

        this.scooterColor = scooterColor;

    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] initParamsForTest() {
        return new Object[][] {
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @After
    @Step("Очистка данных после теста")
    public void clearAfterTests() {
        if (trackId == null) return;

        deleteOrder(trackId);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Тест проверяет API создания заказа. Ожидаемый результат - заказ создан, возвращается его track-номер")
    public void createOrderIsSuccess() {
        Allure.parameter("Цвет самоката", scooterColor);

        Response response = createOrder(firstName, lastName, address, phone, rentTime, deliveryDate, comment, scooterColor);
        checkStatusCode(response, 201);
        checkResponseParamNotNull(response, "track");

        this.trackId = getTrack(response);
    }

}
