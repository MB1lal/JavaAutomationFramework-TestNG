package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Downloads through plain HTTP instead of driving the browser's download UI.
 * Far less flaky and works the same on every machine and browser.
 * The file list on the demo page changes over time, so tests download
 * whatever is listed first rather than a hardcoded name.
 */
public class FileDownloadPage extends BasePage {

    private static final By FILE_LINKS = By.cssSelector("#content a");

    public FileDownloadPage(WebDriver driver) {
        super(driver);
    }

    public List<String> availableFiles() {
        return driver.findElements(FILE_LINKS).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .toList();
    }

    public Path downloadFirstFile(Path targetDir) {
        WebElement first = driver.findElements(FILE_LINKS).stream()
                .filter(link -> !link.getText().trim().isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No files listed to download"));
        String fileName = first.getText().trim();
        String href = first.getAttribute("href");
        Path target = targetDir.resolve(fileName);
        try (InputStream input = new URI(href).toURL().openStream()) {
            Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IllegalStateException("Could not download " + href, e);
        }
        return target;
    }
}
