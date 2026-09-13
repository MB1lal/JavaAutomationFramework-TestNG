package com.automation.runner;

import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds TestNG suites in code instead of static XML files.
 *
 * <p>Which tests land in the suite is decided at runtime from simple inputs
 * (suite name, groups, parallel mode, thread count), so new test classes are
 * picked up automatically as long as they live in the scanned packages and
 * carry the right groups — no XML editing.
 */
public final class SuiteGenerator {

    public static final String API_PACKAGE = "com.automation.tests.api";
    public static final String UI_PACKAGE = "com.automation.tests.ui";

    private SuiteGenerator() {
    }

    public record SuiteOptions(String suite, List<String> groups, String parallel, int threads) {
        public static SuiteOptions defaults() {
            return new SuiteOptions("all", List.of(), "methods", 4);
        }
    }

    public static XmlSuite generate(SuiteOptions options) {
        XmlSuite suite = new XmlSuite();
        suite.setName("Dynamic suite (" + options.suite() + ")");
        XmlSuite.ParallelMode mode = XmlSuite.ParallelMode.getValidParallel(options.parallel());
        if (mode == null) {
            throw new IllegalArgumentException(
                    "Unknown parallel mode '" + options.parallel()
                            + "'. Use methods, tests, classes or instances.");
        }
        suite.setParallel(mode);
        suite.setThreadCount(options.threads());
        suite.setVerbose(2);

        switch (options.suite().toLowerCase()) {
            case "api" -> addTest(suite, "API tests", API_PACKAGE,
                    options.groups().isEmpty() ? List.of("api") : options.groups());
            case "ui" -> addTest(suite, "UI tests", UI_PACKAGE,
                    options.groups().isEmpty() ? List.of("ui") : options.groups());
            case "all" -> {
                addTest(suite, "API tests", API_PACKAGE,
                        options.groups().isEmpty() ? List.of("api") : options.groups());
                addTest(suite, "UI tests", UI_PACKAGE,
                        options.groups().isEmpty() ? List.of("ui") : options.groups());
            }
            default -> throw new IllegalArgumentException(
                    "Unknown suite '" + options.suite() + "'. Use all, api or ui.");
        }
        return suite;
    }

    private static void addTest(XmlSuite suite, String testName, String testPackage, List<String> groups) {
        XmlTest test = new XmlTest(suite);
        test.setName(testName);
        test.setXmlPackages(List.of(new XmlPackage(testPackage)));
        if (!groups.isEmpty()) {
            test.setIncludedGroups(new ArrayList<>(groups));
        }
    }
}
