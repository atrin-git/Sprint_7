package org.example.OrdersTests;

import io.restassured.response.Response;
import org.example.ScooterUrls;

import static io.restassured.RestAssured.given;

public class OrdersAPIHelper {
    public static Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ScooterUrls.HOST_NAME + ScooterUrls.CREATE_ORDER);
    }

    public static Response deleteOrder(Integer trackId) {
        return given()
                .queryParam("track",trackId)
                .when()
                .put(ScooterUrls.HOST_NAME + ScooterUrls.DELETE_ORDER);
    }
}
