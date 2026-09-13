package com.automation.base;

import com.automation.config.ConfigReader;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

/**
 * Every API test extends this. Points RestAssured at the system under test once per class.
 */
public abstract class ApiBaseTest {

    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        RestAssured.baseURI = ConfigReader.getInstance().get("base.uri");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
