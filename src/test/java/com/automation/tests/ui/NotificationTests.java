package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.NotificationPage;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code notification.feature}.
 * The demo page shows one of two messages at random, so either is accepted.
 */
@Test(groups = "ui")
public class NotificationTests extends UiBaseTest {

    private static final List<String> EXPECTED_MESSAGES = List.of(
            "Action successful",
            "Action unsuccesful, please try again");

    @Test(description = "Generated notification is one of the known messages",
            invocationCount = 2)
    public void notificationIsRecognised() {
        NotificationPage notifications =
                new HomePage(driver()).open().goTo("notification messages");
        notifications.generateNotification();

        assertThat(notifications.notificationText())
                .as("Unexpected notification message")
                .isIn(EXPECTED_MESSAGES);
    }
}
