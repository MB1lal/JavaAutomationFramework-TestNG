package com.automation.base;

import com.automation.driver.DriverFactory;
import com.automation.listeners.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Every UI test extends this. A fresh browser is opened before each test
 * method and closed afterwards, so tests never leak state into each other.
 *
 * <p>Note the driver is kept in a {@code ThreadLocal} inside
 * {@link com.automation.driver.DriverFactory} and exposed here via
 * {@link #driver()}. Tests must NOT store the driver or page objects in
 * instance fields, because TestNG may run methods of the same class on
 * different threads sharing one test class instance.
 */
@Listeners(TestListener.class)
public abstract class UiBaseTest {

    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.createDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }
}
