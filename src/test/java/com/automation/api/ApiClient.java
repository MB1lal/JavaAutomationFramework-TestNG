package com.automation.api;

import com.automation.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Shared request setup for every API client: base URL plus JSON content type.
 */
public abstract class ApiClient {

    protected RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getInstance().get("base.uri"))
                .setContentType(ContentType.JSON)
                .build();
    }
}
