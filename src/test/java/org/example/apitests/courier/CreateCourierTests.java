package org.example.apitests.courier;

import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
import org.example.resthandlers.apihandlers.CourierAPIHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-CreateCourier", name = "#api-Courier-CreateCourier")
@Tag("create-courier")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API создания курьера")
@DisplayName("1. Создание курьера")
public class CreateCourierTests extends CourierAPIHandler {
    private String login;
    private String password;
    private String firstName;

    public CreateCourierTests() {
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @After
    @Step("Очистка данных после теста")
    public void cleanAfterTests() {
        if (!isCourierCreated()) return;

        Integer idCourier = getIdCourier(loginCourier(login, password));

        if (idCourier != null) {
            deleteCourier(idCourier);
        }

        setIsCreated(false);
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Тест проверяет API создания нового курьера. Ожидаемый результат - новый курьер создан")
    public void createNewCourierIsSuccess() {
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Тест проверяет возможность создать двух одинаковых курьеров. Ожидаемый результат - одинаковых курьеров создать нельзя")
    public void createSameCouriersIsFailed(){
        // Создание первого курьера
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        // Создание второго курьера
        response = createCourier(login, password, firstName);

        checkStatusCode(response, 409);
        checkMessage(response, "message", "Этот логин уже используется");
    }

    @Test
    @DisplayName("Создание нового курьера без входных данных")
    @Description("Тест проверяет API создания нового курьера без входных данных. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingAllParamsIsFailed() {
        Response response = createCourier("", "", "");
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Тест проверяет API создания нового курьера без логина. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingLoginParamIsFailed() {
        Response response = createCourier("", password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без пароля")
    @Description("Тест проверяет API создания нового курьера без пароля. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingPasswordParamIsFailed() {
        Response response = createCourier(login, "", firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }
}
