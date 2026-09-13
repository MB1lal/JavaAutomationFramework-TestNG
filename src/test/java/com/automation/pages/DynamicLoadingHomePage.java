package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DynamicLoadingHomePage extends BasePage {

    @FindBy(partialLinkText = "Example 1")
    private WebElement exampleOneLink;

    @FindBy(partialLinkText = "Example 2")
    private WebElement exampleTwoLink;

    public DynamicLoadingHomePage(WebDriver driver) {
        super(driver);
    }

    public DynamicLoadingExamplePage openExample(int number) {
        if (number == 1) {
            click(exampleOneLink);
        } else if (number == 2) {
            click(exampleTwoLink);
        } else {
            throw new IllegalArgumentException("Example must be 1 or 2");
        }
        return new DynamicLoadingExamplePage(driver);
    }
}
