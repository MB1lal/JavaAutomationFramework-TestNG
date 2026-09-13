package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NotificationPage extends BasePage {

    @FindBy(css = "p a")
    private WebElement clickHereLink;

    @FindBy(id = "flash")
    private WebElement notification;

    public NotificationPage(WebDriver driver) {
        super(driver);
    }

    public void generateNotification() {
        click(clickHereLink);
    }

    public String notificationText() {
        return textOf(notification).replace("×", "").trim();
    }
}
