package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HoversPage extends BasePage {

    @FindBy(css = ".figure")
    private List<WebElement> avatars;

    @FindBy(css = ".figure h5")
    private List<WebElement> profileNames;

    public HoversPage(WebDriver driver) {
        super(driver);
    }

    public void hoverOverAvatar(int index) {
        hoverOver(avatars.get(index));
    }

    public String profileName(int index) {
        return textOf(profileNames.get(index));
    }
}
