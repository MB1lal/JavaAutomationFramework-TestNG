package com.automation.base;

import com.automation.config.ConfigReader;
import com.automation.listeners.TestListener;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Every API test extends this. Points RestAssured at the system under test once per class.
 */
@Listeners(TestListener.class)
public abstract class ApiBaseTest {

    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        RestAssured.baseURI = ConfigReader.getInstance().get("base.uri");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
