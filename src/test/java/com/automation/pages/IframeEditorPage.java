package com.automation.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IframeEditorPage extends BasePage {

    @FindBy(id = "mce_0_ifr")
    private WebElement editorFrame;

    @FindBy(id = "tinymce")
    private WebElement editorBody;

    public IframeEditorPage(WebDriver driver) {
        super(driver);
    }

    public void write(String text) {
        // TinyMCE swallows raw Selenium keystrokes in headless runs, so write
        // through the editor's own API. The visible result is identical.
        // activeEditor exists before initialisation finishes and anything set
        // too early gets wiped, so wait for initialised, then verify the write.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(d -> Boolean.TRUE.equals(js.executeScript(
                "return typeof tinymce !== 'undefined' && !!tinymce.activeEditor"
                        + " && tinymce.activeEditor.initialized;")));
        js.executeScript("tinymce.activeEditor.setContent(arguments[0]);", text);
        wait.until(d -> String.valueOf(
                js.executeScript("return tinymce.activeEditor.getContent();")).contains(text));
    }

    public String content() {
        driver.switchTo().frame(editorFrame);
        try {
            return textOf(editorBody).trim();
        } finally {
            driver.switchTo().defaultContent();
        }
    }
}
