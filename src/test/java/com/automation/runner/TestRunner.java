package com.automation.runner;

import com.automation.runner.SuiteGenerator.SuiteOptions;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modern entry point for running the framework without touching XML files.
 *
 * <p>Examples (after {@code mvn test-compile}):
 * <pre>
 *   mvn exec:java -Dexec.args="--suite=api"
 *   mvn exec:java -Dexec.args="--suite=ui --browser=firefox --headless=false --threads=2"
 *   mvn exec:java -Dexec.args="--suite=all --groups=smoke --dry-run"
 * </pre>
 *
 * <p>Every flag also works as a system property ({@code -Dsuite=api ...}),
 * so CI can drive the runner without changing the command shape.
 */
public final class TestRunner {

    private TestRunner() {
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> flags = parse(args);

        String suite = value(flags, "suite", "all");
        List<String> groups = value(flags, "groups", "").isBlank()
                ? List.of()
                : Arrays.stream(value(flags, "groups", "").split(","))
                        .map(String::trim).filter(s -> !s.isEmpty()).toList();
        String parallel = value(flags, "parallel", "methods");
        int threads = Integer.parseInt(value(flags, "threads",
                suite.equalsIgnoreCase("ui") ? "2" : "4"));
        boolean dryRun = Boolean.parseBoolean(value(flags, "dry-run", "false"));
        Path out = Paths.get(value(flags, "out", "test-output/dynamic-testng.xml"));

        // Forwarded to ConfigReader (system properties win over config.properties).
        System.setProperty("browser", value(flags, "browser", System.getProperty("browser", "chrome")));
        System.setProperty("headless", value(flags, "headless", System.getProperty("headless", "true")));

        XmlSuite xmlSuite = SuiteGenerator.generate(
                new SuiteOptions(suite, groups, parallel, threads));

        Files.createDirectories(out.getParent());
        Files.writeString(out, xmlSuite.toXml());
        System.out.println("Suite written to " + out.toAbsolutePath());

        if (dryRun) {
            System.out.println("Dry run — not executing.");
            return;
        }

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(xmlSuite));
        testng.run();
        System.exit(testng.getStatus());
    }

    private static Map<String, String> parse(String[] args) {
        Map<String, String> flags = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("--") && arg.contains("=")) {
                String[] parts = arg.substring(2).split("=", 2);
                flags.put(parts[0].toLowerCase(), parts[1]);
            }
        }
        return flags;
    }

    private static String value(Map<String, String> flags, String key, String fallback) {
        if (flags.containsKey(key)) {
            return flags.get(key);
        }
        String systemValue = System.getProperty(key);
        return systemValue != null ? systemValue : fallback;
    }
}
