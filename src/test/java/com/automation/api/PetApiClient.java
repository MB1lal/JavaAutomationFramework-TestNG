package com.automation.api;

import com.automation.models.Pet;
import com.automation.utils.JsonUtils;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Wraps the {@code /pet} endpoints of the Petstore API.
 */
public class PetApiClient extends ApiClient {

    private static final String PET_PATH = "/pet";

    public Response addPet(Pet pet) {
        return given()
                .spec(baseSpec())
                .body(JsonUtils.toJson(pet))
                .when()
                .post(PET_PATH)
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response getPetById(long petId) {
        return given()
                .spec(baseSpec())
                .when()
                .get(PET_PATH + "/" + petId)
                .then()
                .extract().response();
    }

    public Response getPetsByStatus(List<String> statuses) {
        return given()
                .spec(baseSpec())
                .queryParam("status", String.join(",", statuses))
                .when()
                .get(PET_PATH + "/findByStatus")
                .then()
                .statusCode(200)
                .extract().response();
    }

    public void deletePet(long petId) {
        given()
                .spec(baseSpec())
                .when()
                .delete(PET_PATH + "/" + petId)
                .then()
                .statusCode(200);
    }

    public void updatePetWithForm(long petId, String field, String value) {
        given()
                .spec(baseSpec())
                .contentType("application/x-www-form-urlencoded")
                .formParam(field, value)
                .when()
                .post(PET_PATH + "/" + petId)
                .then()
                .statusCode(200);
    }
}
