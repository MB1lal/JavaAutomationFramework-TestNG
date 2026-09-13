package com.automation.api;

import com.automation.models.User;
import com.automation.utils.JsonUtils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Wraps the {@code /user} endpoints of the Petstore API.
 */
public class UserApiClient extends ApiClient {

    private static final String USER_PATH = "/user";

    public void createUser(User user) {
        given()
                .spec(baseSpec())
                .body(JsonUtils.toJson(user))
                .when()
                .post(USER_PATH)
                .then()
                .statusCode(200);
    }

    public Response getUser(String username) {
        return given()
                .spec(baseSpec())
                .when()
                .get(USER_PATH + "/" + username)
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response login(String username, String password) {
        return given()
                .spec(baseSpec())
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(USER_PATH + "/login")
                .then()
                .statusCode(200)
                .extract().response();
    }

    public void logout() {
        given()
                .spec(baseSpec())
                .when()
                .get(USER_PATH + "/logout")
                .then()
                .statusCode(200);
    }

    public Response deleteUser(String username) {
        return given()
                .spec(baseSpec())
                .when()
                .delete(USER_PATH + "/" + username)
                .then()
                .extract().response();
    }
}
