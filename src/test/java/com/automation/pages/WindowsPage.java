package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class WindowsPage extends BasePage {

    @FindBy(css = "div.example a")
    private WebElement clickHereLink;

    @FindBy(css = "h3")
    private WebElement header;

    public WindowsPage(WebDriver driver) {
        super(driver);
    }

    public void openNewWindow() {
        click(clickHereLink);
    }

    public void switchToNewestWindow() {
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(handles.get(handles.size() - 1));
    }

    public void switchToOriginalWindow() {
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(handles.get(0));
    }

    public String headerText() {
        return textOf(header);
    }
}
