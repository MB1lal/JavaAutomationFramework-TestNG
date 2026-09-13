package com.automation.api;

import com.automation.models.Order;
import com.automation.utils.JsonUtils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Wraps the {@code /store} endpoints of the Petstore API.
 */
public class StoreApiClient extends ApiClient {

    private static final String ORDER_PATH = "/store/order";

    public void placeOrder(Order order) {
        given()
                .spec(baseSpec())
                .body(JsonUtils.toJson(order))
                .when()
                .post(ORDER_PATH)
                .then()
                .statusCode(200);
    }

    public Response getOrder(int orderId) {
        return given()
                .spec(baseSpec())
                .when()
                .get(ORDER_PATH + "/" + orderId)
                .then()
                .statusCode(200)
                .extract().response();
    }

    public void deleteOrder(int orderId) {
        given()
                .spec(baseSpec())
                .when()
                .delete(ORDER_PATH + "/" + orderId)
                .then()
                .statusCode(200);
    }

    public void assertOrderIsGone(int orderId) {
        given()
                .spec(baseSpec())
                .when()
                .get(ORDER_PATH + "/" + orderId)
                .then()
                .statusCode(404);
    }
}
