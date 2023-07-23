package org.example.CourierTests;

import io.restassured.response.Response;
import org.example.CourierTests.Courier;
import org.example.ScooterUrls;

import static io.restassured.RestAssured.given;

public class CourierAPIHelper {
    public static Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(ScooterUrls.HOST_NAME + ScooterUrls.CREATE_COURIER);
    }

    public static Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(ScooterUrls.HOST_NAME + ScooterUrls.LOGIN_COURIER);

    }

    public static Response deleteCourier(Integer idCourier) {
        return given()
                .delete(ScooterUrls.HOST_NAME + ScooterUrls.DELETE_COURIER + idCourier);

    }
}
