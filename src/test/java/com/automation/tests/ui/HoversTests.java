package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.HoversPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code hover.feature}.
 */
@Test(groups = "ui")
public class HoversTests extends UiBaseTest {

    @DataProvider(name = "users")
    public Object[][] users() {
        return new Object[][]{
                {0, "name: user1"},
                {1, "name: user2"},
                {2, "name: user3"},
        };
    }

    @Test(description = "Hovering an avatar reveals the profile name", dataProvider = "users")
    public void hoverRevealsName(int index, String expectedName) {
        HoversPage hovers = new HomePage(driver()).open().goTo("hovers");
        hovers.hoverOverAvatar(index);

        assertThat(hovers.profileName(index)).isEqualTo(expectedName);
    }
}
