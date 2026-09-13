package com.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Paths;

/**
 * One ExtentReports instance for the whole run. The report lands in
 * {@code test-output/extent-report.html}.
 */
public final class ExtentManager {

    private static volatile ExtentReports extent;

    private ExtentManager() {
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (ExtentManager.class) {
                if (extent == null) {
                    String reportPath = Paths.get(System.getProperty("user.dir"),
                            "test-output", "extent-report.html").toString();
                    ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
                    spark.config().setTheme(Theme.STANDARD);
                    spark.config().setDocumentTitle("Automation Report");
                    spark.config().setReportName("TestNG Regression");
                    extent = new ExtentReports();
                    extent.attachReporter(spark);
                    extent.setSystemInfo("Java", System.getProperty("java.version"));
                    extent.setSystemInfo("OS", System.getProperty("os.name"));
                }
            }
        }
        return extent;
    }
}
