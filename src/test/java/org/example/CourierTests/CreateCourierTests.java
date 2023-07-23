package org.example.CourierTests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;

@Issue("1")
@Tag("create-courier")
@DisplayName("1. Создание курьера")
public class CreateCourierTests {
    private final Map<String,String> testData;

    public CreateCourierTests() {
        testData = new HashMap<>();
        testData.put("login", "testLoginNewCourier");
        testData.put("password", "1234");
        testData.put("firstName", "saske");
    }
    private boolean isCreated = false;
    @After
    public void cleanAfterTests() {
        if (!isCreated) return;

        Response responseLogin = CourierAPIHelper.loginCourier(new Courier(
                testData.get("login"),
                testData.get("password")
        ));
        Integer idCourier = responseLogin.body().as(CourierLoginResponse.class).getId();
        if (idCourier != null) {
            CourierAPIHelper.deleteCourier(idCourier);
        }

        this.isCreated = false;
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Тест проверяет API создания нового курьера. Ожидаемый результат - новый курьер создан")
    public void createNewCourierIsSuccess() {
        Courier courier = new Courier(
                testData.get("login"),
                testData.get("password"),
                testData.get("firstName")
        );
        Response response = CourierAPIHelper.createCourier(courier);
        response.then().statusCode(201).and().assertThat().body("ok", equalTo(true));
        this.isCreated = true;
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Тест проверяет возможность создать двух одинаковых курьеров. Ожидаемый результат - одинаковых курьеров создать нельзя")
    public void createSameCouriersIsFailed() {
        Courier courier = new Courier(
                testData.get("login"),
                testData.get("password"),
                testData.get("firstName")
        );

        // Создание первого курьера
        CourierAPIHelper.createCourier(courier)
                .then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
        this.isCreated = true;

        // Создание второго курьера
        CourierAPIHelper.createCourier(courier)
                .then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание нового курьера без входных данных")
    @Description("Тест проверяет API создания нового курьера без входных данных. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingAllParamsIsFailed() {
        Courier courier = new Courier();
        CourierAPIHelper.createCourier(courier)
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Тест проверяет API создания нового курьера без логина. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingLoginParamIsFailed() {
        Courier courier = new Courier(
                "",
                testData.get("password"),
                testData.get("firstName"));

        Response response = CourierAPIHelper.createCourier(courier);

        if (response.getStatusCode() == 201)
            this.isCreated = true;

        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание нового курьера без пароля")
    @Description("Тест проверяет API создания нового курьера без пароля. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingPasswordParamIsFailed() {
        Courier courier = new Courier(
                testData.get("login"),
                "",
                testData.get("firstName"));

        Response response = CourierAPIHelper.createCourier(courier);

        if (response.getStatusCode() == 201)
            this.isCreated = true;

        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
