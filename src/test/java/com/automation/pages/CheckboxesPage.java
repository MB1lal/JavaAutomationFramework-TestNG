package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckboxesPage extends BasePage {

    @FindBy(css = "#checkboxes > input:first-of-type")
    private WebElement firstCheckbox;

    @FindBy(css = "#checkboxes > input:nth-of-type(2)")
    private WebElement secondCheckbox;

    public CheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public void toggle(int index) {
        click(checkbox(index));
    }

    public boolean isSelected(int index) {
        return checkbox(index).isSelected();
    }

    private WebElement checkbox(int index) {
        return switch (index) {
            case 1 -> firstCheckbox;
            case 2 -> secondCheckbox;
            default -> throw new IllegalArgumentException("Checkbox index must be 1 or 2");
        };
    }
}
