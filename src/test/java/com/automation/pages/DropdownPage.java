package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage extends BasePage {

    @FindBy(id = "dropdown")
    private WebElement dropdown;

    public DropdownPage(WebDriver driver) {
        super(driver);
    }

    public void selectByVisibleText(String option) {
        new Select(dropdown).selectByVisibleText(option);
    }

    public String selectedOption() {
        return new Select(dropdown).getFirstSelectedOption().getText();
    }
}
