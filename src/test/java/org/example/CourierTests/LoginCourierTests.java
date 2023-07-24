package org.example.CourierTests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@Issue("2")
@Tag("login-courier")
@DisplayName("2. Логин курьера")
public class LoginCourierTests {
    private final Map<String,String> testData;

    public LoginCourierTests() {
        testData = new HashMap<>();
        testData.put("login", "testLogin_" + this.getClass().getName());
        testData.put("password", "1234");
        testData.put("firstName", "saske");
    }

//    @BeforeClass
//    public static void beforeAll() {
//        RestAssured.requestSpecification = new RequestSpecBuilder().build().filter(new AllureRestAssured());
//    }

    @Before
    public void prepareTestData() {
        CourierAPIHelper.createCourier(new Courier(
                testData.get("login"),
                testData.get("password"),
                testData.get("firstName")
        ));
    }

    @After
    public void clearAfterTests() {
        Response response = CourierAPIHelper.loginCourier(new Courier(
                testData.get("login"),
                testData.get("password")
        ));

        Integer idCourier = response.body().as(CourierLoginResponse.class).getId();

        if (idCourier == null) return;

        CourierAPIHelper.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Логин курьера в систему")
    @Description("Тест проверяет API логина курьера. Ожидаемый результат - курьер залогинен в системе, возвращается его id")
    public void loginCourierIsSuccess() {

        Allure.step("Логин курьера в систему");
        Response response = CourierAPIHelper.loginCourier(new Courier(
                testData.get("login"),
                testData.get("password")
        ));

        response.then().statusCode(200).and().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера в систему без входных данных")
    @Description("Тест проверяет API логина курьера без входных данных. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierMissingAllParamsIsFailed() {
        Courier courier = new Courier(
                "",
                ""
        );

        CourierAPIHelper.loginCourier(courier)
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Логин курьера в систему без логина")
    @Description("Тест проверяет API логина курьера без логина. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierMissingLoginParamIsFailed() {
        Courier courier = new Courier(
                "",
                testData.get("password")
        );

        CourierAPIHelper.loginCourier(courier)
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера в систему без пароля")
    @Description("Тест проверяет API логина курьера без пароля. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierMissingPasswordParamIsFailed() {
        Courier courier = new Courier(
                testData.get("login"),
                ""
        );

        CourierAPIHelper.loginCourier(courier)
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера в систему с некорректным логином")
    @Description("Тест проверяет API логина курьера с некорректным логином. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierIncorrectLoginParamIsFailed() {
        Courier courier = new Courier(
                testData.get("login") + "_incorrect",
                testData.get("password")
        );

        CourierAPIHelper.loginCourier(courier)
                .then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин курьера в систему с некорректным паролем")
    @Description("Тест проверяет API логина курьера с некорректным паролем. Ожидаемый результат - курьер НЕ залогинен в системе")
    public void loginCourierIncorrectPasswordParamIsFailed() {
        Courier courier = new Courier(
                testData.get("login"),
                testData.get("password") + "_incorrect"
        );

        CourierAPIHelper.loginCourier(courier)
                .then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}
