package com.automation.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JsAlertsPage extends BasePage {

    @FindBy(css = "button[onclick='jsAlert()']")
    private WebElement jsAlertButton;

    @FindBy(css = "button[onclick='jsConfirm()']")
    private WebElement jsConfirmButton;

    @FindBy(css = "button[onclick='jsPrompt()']")
    private WebElement jsPromptButton;

    @FindBy(id = "result")
    private WebElement result;

    public JsAlertsPage(WebDriver driver) {
        super(driver);
    }

    public void trigger(AlertType type) {
        click(switch (type) {
            case ALERT -> jsAlertButton;
            case CONFIRM -> jsConfirmButton;
            case PROMPT -> jsPromptButton;
        });
    }

    public void typeIntoPrompt(String text) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
    }

    public void accept() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void dismiss() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    public String resultText() {
        return textOf(result);
    }

    public enum AlertType {
        ALERT, CONFIRM, PROMPT
    }
}
