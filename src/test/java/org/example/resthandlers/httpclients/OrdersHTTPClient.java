package org.example.resthandlers.httpclients;

import io.restassured.response.Response;
import org.example.apitests.entities.requestentities.Order;
import org.example.ScooterUrls;

import java.util.HashMap;
import java.util.Map;

public class OrdersHTTPClient extends BaseHTTPClient {
    public Response createOrder(Order order) {
        return doPostRequest(
                ScooterUrls.HOST_NAME + ScooterUrls.CREATE_ORDER,
                order,
                "application/json"
        );
    }

    public Response deleteOrder(Integer trackId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("track", trackId);

        return doPutRequest(
                ScooterUrls.HOST_NAME + ScooterUrls.DELETE_ORDER,
                queryParams
        );
    }

    public Response getOrdersList() {
        return doGetRequest(ScooterUrls.HOST_NAME + ScooterUrls.GET_ORDERS_LIST);
    }

}
