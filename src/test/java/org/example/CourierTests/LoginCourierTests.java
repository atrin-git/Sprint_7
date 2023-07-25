package org.example.CourierTests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-Login", name = "#api-Courier-Login")
@Tag("login-courier")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API логина курьера")
@DisplayName("2. Логин курьера")
public class LoginCourierTests extends CourierAPITests{
    private final String login;
    private final String password;
    private final String firstName;

    public LoginCourierTests() {
        login = "courier_" + this.getClass().getSimpleName();
        password = "1234";
        firstName = "Name";
    }

    @Before
    @Step("Подготовка данных для тестирования")
    public void prepareTestData() {
        createCourier(login, password, firstName);
    }

    @After
    @Step("Очистка данных после теста")
    public void clearAfterTests() {
        Integer idCourier = getIdCourier(loginCourier(login, password));
        if (idCourier == null) return;

        deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Логин курьера в систему")
    @Description("Тест проверяет API логина курьера. Ожидаемый результат - курьер залогинен в системе, возвращается его id")
    public void loginCourierIsSuccess() {
        Response response = loginCourier(login,password);

        checkStatusCode(response, 200);
        checkCourierIDNotNull(response);
    }

    @Test
    @DisplayName("Логин курьера в систему без входных данных")
    @Description("Тест проверяет API логина курьера без входных данных. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierMissingAllParamsIsFailed() {
        Response response = loginCourier("", "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера в систему без логина")
    @Description("Тест проверяет API логина курьера без логина. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierMissingLoginParamIsFailed() {
        Response response = loginCourier("", password);

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера в систему без пароля")
    @Description("Тест проверяет API логина курьера без пароля. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierMissingPasswordParamIsFailed() {
        Response response = loginCourier(login, "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера в систему с некорректным логином")
    @Description("Тест проверяет API логина курьера с некорректным логином. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierIncorrectLoginParamIsFailed() {
        Response response = loginCourier(login + "1", password);

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Логин курьера в систему с некорректным паролем")
    @Description("Тест проверяет API логина курьера с некорректным паролем. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierIncorrectPasswordParamIsFailed() {
        Response response = loginCourier(login, password + "1");

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }
}
