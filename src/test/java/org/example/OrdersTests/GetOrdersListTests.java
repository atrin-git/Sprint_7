package org.example.OrdersTests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.example.CourierTests.CourierAPITests;
import org.example.CourierTests.CreateCourierTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CreateOrder", name = "#api-Orders-GetOrdersPageByPage")
@Tag("get-orders-list")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API получения списка заказа")
@DisplayName("4. Получение списка заказов")
public class GetOrdersListTests extends OrdersAPITests {
//    private Integer courierId;
//    private String nearestStation;
//    private Integer limit;
//    private Integer page;
//
//    private final CourierAPITests courierAPITests = new CreateCourierTests();
//
//    @Before
//    @Step("Подготовка данных для теста")
//    public void prepareTestData() {
//        String login = "courier_" + this.getClass().getSimpleName(),
//            password = "1234",
//            firstName = "Name";
//
//        Response response = courierAPITests.createCourier(login, password, firstName);
//
//        courierAPITests.setIsCreated(courierAPITests.isCourierCreated(response, 201));
//
//        courierId = courierAPITests.getIdCourier(courierAPITests.loginCourier(login, password));
//    }
//
//    @After
//    @Step("Удаление тестовых данных")
//    public void clearTestData() {
//        if (courierId == null) return;
//
//        courierAPITests.deleteCourier(courierId);
//    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Тест проверяет API получения списка заказов. Ожидаемый результат - возвращается список заказов")
    public void getOrderListWithoutParamsIsSuccess() {
        Response response = getOrdersList();

        checkStatusCode(response, 200);
        checkOrdersInResponse(response);
    }
}
