package com.automation.pages;

import com.automation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for every page object. Wraps the driver with explicit waits
 * so page classes stay small and never call Thread.sleep().
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final int timeoutSeconds;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.timeoutSeconds = ConfigReader.getInstance().getInt("explicit.wait.seconds", 15);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        PageFactory.initElements(driver, this);
    }

    protected void open(String url) {
        driver.get(url);
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(WebElement element, String text) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(element));
        field.clear();
        field.sendKeys(text);
    }

    protected String textOf(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected boolean isVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitForInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void hoverOver(WebElement element) {
        new Actions(driver).moveToElement(wait.until(ExpectedConditions.visibilityOf(element))).perform();
    }

    protected String pageSource() {
        return driver.getPageSource();
    }

    protected String currentUrl() {
        return driver.getCurrentUrl();
    }
}
