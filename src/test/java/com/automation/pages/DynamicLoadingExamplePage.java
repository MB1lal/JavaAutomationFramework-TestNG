package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DynamicLoadingExamplePage extends BasePage {

    @FindBy(css = "#start > button")
    private WebElement startButton;

    @FindBy(id = "finish")
    private WebElement loadedText;

    private static final By LOADING_BAR = By.id("loading");

    public DynamicLoadingExamplePage(WebDriver driver) {
        super(driver);
    }

    public void start() {
        click(startButton);
    }

    public String waitForLoadedText() {
        waitForInvisible(LOADING_BAR);
        return textOf(loadedText);
    }
}
