package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code authentication.feature}:
 * valid login, invalid login, logout.
 */
@Test(groups = "ui")
public class LoginTests extends UiBaseTest {

    private LoginPage openLoginPage() {
        return new HomePage(driver()).open().goTo("form authentication");
    }

    @Test(description = "Valid login lands on the secure area")
    public void validLogin() {
        LoginPage loginPage = openLoginPage();
        loginPage.loginAs("tomsmith", "SuperSecretPassword!");

        assertThat(loginPage.isLoggedIn()).as("User should be logged in").isTrue();
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"invalid_user", "SuperSecretPassword!", "Your username is invalid!"},
                {"tomsmith", "invalid_password", "Your password is invalid!"},
        };
    }

    @Test(description = "Invalid login shows an error", dataProvider = "invalidCredentials")
    public void invalidLogin(String username, String password, String expectedError) {
        LoginPage loginPage = openLoginPage();
        loginPage.loginAs(username, password);

        assertThat(driver().getPageSource()).contains(expectedError);
    }

    @Test(description = "Logout returns to the login page")
    public void logout() {
        LoginPage loginPage = openLoginPage();
        loginPage.loginAs("tomsmith", "SuperSecretPassword!");
        assertThat(loginPage.isLoggedIn()).isTrue();

        loginPage.logout();

        assertThat(loginPage.isLoggedOut()).as("User should be logged out").isTrue();
        assertThat(driver().getCurrentUrl()).contains("/login");
    }
}
