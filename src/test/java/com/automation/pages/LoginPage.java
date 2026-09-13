package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By FLASH_MESSAGE = By.id("flash");

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".icon-2x.icon-signout")
    private WebElement logoutButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginAs(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        // Both outcomes render a flash message, so wait for it instead of
        // asserting against a page that may still be navigating.
        wait.until(ExpectedConditions.visibilityOfElementLocated(FLASH_MESSAGE));
    }

    public boolean isLoggedIn() {
        return pageSource().contains("You logged into a secure area!");
    }

    public void logout() {
        click(logoutButton);
        wait.until(ExpectedConditions.urlContains("/login"));
    }

    public boolean isLoggedOut() {
        return pageSource().contains("You logged out of the secure area!");
    }
}
