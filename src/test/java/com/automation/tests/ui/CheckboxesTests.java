package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.CheckboxesPage;
import com.automation.pages.HomePage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code checkboxes.feature}.
 */
@Test(groups = "ui")
public class CheckboxesTests extends UiBaseTest {

    @Test(description = "Toggling checkboxes flips their state")
    public void toggleCheckboxes() {
        CheckboxesPage checkboxes = new HomePage(driver()).open().goTo("checkboxes");

        checkboxes.toggle(1);
        assertThat(checkboxes.isSelected(1)).as("First checkbox should be selected").isTrue();

        checkboxes.toggle(2);
        assertThat(checkboxes.isSelected(2)).as("Second checkbox should be deselected").isFalse();
    }
}
