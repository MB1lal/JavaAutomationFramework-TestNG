package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.DropdownPage;
import com.automation.pages.HomePage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code dropdown.feature}.
 */
@Test(groups = "ui")
public class DropdownTests extends UiBaseTest {

    @Test(description = "Selecting an option shows it as selected")
    public void selectOption() {
        DropdownPage dropdown = new HomePage(driver()).open().goTo("dropdown");

        dropdown.selectByVisibleText("Option 2");

        assertThat(dropdown.selectedOption()).isEqualTo("Option 2");
    }
}
