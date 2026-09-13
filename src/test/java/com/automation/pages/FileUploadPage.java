package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FileUploadPage extends BasePage {

    private static final By FILE_INPUT = By.id("file-upload");
    private static final By UPLOAD_BUTTON = By.id("file-submit");
    private static final By HEADER = By.cssSelector("#content h3");
    private static final By UPLOADED_FILE = By.id("uploaded-files");

    public FileUploadPage(WebDriver driver) {
        super(driver);
    }

    public void upload(String absoluteFilePath) {
        driver.findElement(FILE_INPUT).sendKeys(absoluteFilePath);
        click(driver.findElement(UPLOAD_BUTTON));
        // The form POSTs to a new page, so wait for the result before reading anything.
        wait.until(ExpectedConditions.textToBePresentInElementLocated(HEADER, "File Uploaded!"));
    }

    public String headerText() {
        return textOf(driver.findElement(HEADER));
    }

    public String uploadedFileName() {
        return textOf(driver.findElement(UPLOADED_FILE));
    }
}
