package com.automation.listeners;

import com.automation.driver.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Logs every test to ExtentReports, attaches a screenshot on failure,
 * and gives all tests one automatic retry.
 */
public class TestListener implements ITestListener, IAnnotationTransformer {

    private static final Logger log = LogManager.getLogger(TestListener.class);
    private final ExtentReports extent = ExtentManager.getInstance();
    private final Map<String, ExtentTest> tests = new ConcurrentHashMap<>();

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        if (annotation.getRetryAnalyzerClass() == null) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName(),
                String.valueOf(result.getMethod().getDescription()));
        tests.put(key(result), test);
        log.info("STARTED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        tests.get(key(result)).log(Status.PASS, "Test passed");
        log.info("PASSED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = tests.get(key(result));
        test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        attachScreenshot(test);
        log.error("FAILED: {}", result.getMethod().getMethodName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        tests.get(key(result)).log(Status.SKIP, "Test skipped");
        log.warn("SKIPPED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("Report written to test-output/extent-report.html");
    }

    private void attachScreenshot(ExtentTest test) {
        WebDriver driver;
        try {
            driver = DriverFactory.getDriver();
        } catch (IllegalStateException e) {
            return; // API test, no browser to screenshot.
        }
        try {
            Path dir = Paths.get(System.getProperty("user.dir"), "test-output", "screenshots");
            Files.createDirectories(dir);
            String fileName = "failure-" + System.currentTimeMillis() + ".png";
            Path target = dir.resolve(fileName);
            Files.write(target, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            test.addScreenCaptureFromPath(target.toString());
        } catch (Exception e) {
            log.warn("Could not capture screenshot", e);
        }
    }

    private static String key(ITestResult result) {
        return result.getTestClass().getName() + "." + result.getMethod().getMethodName();
    }
}
