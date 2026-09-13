package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FramesPage extends BasePage {

    @FindBy(linkText = "Nested Frames")
    private WebElement nestedFramesLink;

    @FindBy(linkText = "iFrame")
    private WebElement iframeLink;

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    public NestedFramesPage openNestedFrames() {
        click(nestedFramesLink);
        return new NestedFramesPage(driver);
    }

    public IframeEditorPage openIframe() {
        click(iframeLink);
        return new IframeEditorPage(driver);
    }
}
