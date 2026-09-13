package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.WindowsPage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code multi-window.feature}.
 */
@Test(groups = "ui")
public class WindowTests extends UiBaseTest {

    @Test(description = "New window opens and both windows are reachable")
    public void openAndSwitchWindows() {
        WindowsPage windows = new HomePage(driver()).open().goTo("multiple windows");
        windows.openNewWindow();
        windows.switchToNewestWindow();
        assertThat(windows.headerText()).isEqualTo("New Window");

        windows.switchToOriginalWindow();
        assertThat(windows.headerText()).isEqualTo("Opening a new window");
    }
}
