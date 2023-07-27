package org.example.resthandlers.httpclients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;


public abstract class BaseHTTPClient {

    public Response doPostRequest(String url, Object requestBody, String contentType) {
        return given(this.baseRequest(contentType))
                .body(requestBody)
                .when()
                .post(url);
    }

    public Response doGetRequest(String url) {
        return given(this.baseRequest())
                .get(url);
    }

    public Response doGetRequest(String url, Map<String, Object> queryParams) {
        return given(this.baseRequest())
                .queryParams(queryParams)
                .when()
                .get(url);
    }

    public Response doDeleteRequest(String url) {
        return given(this.baseRequest())
                .delete(url);
    }

    public Response doPutRequest(String url, Map<String, Object> queryParams) {
        return given(this.baseRequest())
                .queryParams(queryParams)
                .when()
                .put(url);
    }

    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    private RequestSpecification baseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
}
