package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.JsAlertsPage;
import org.testng.annotations.Test;

import static com.automation.pages.JsAlertsPage.AlertType.ALERT;
import static com.automation.pages.JsAlertsPage.AlertType.CONFIRM;
import static com.automation.pages.JsAlertsPage.AlertType.PROMPT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code js-alert.feature}.
 */
@Test(groups = "ui")
public class JsAlertTests extends UiBaseTest {

    private JsAlertsPage openAlertsPage() {
        return new HomePage(driver()).open().goTo("javascript alerts");
    }

    @Test(description = "JS alert accepted")
    public void jsAlert() {
        JsAlertsPage alerts = openAlertsPage();
        alerts.trigger(ALERT);
        alerts.accept();

        assertThat(alerts.resultText()).isEqualTo("You successfully clicked an alert");
    }

    @Test(description = "JS confirm dismissed")
    public void jsConfirm() {
        JsAlertsPage alerts = openAlertsPage();
        alerts.trigger(CONFIRM);
        alerts.dismiss();

        assertThat(alerts.resultText()).isEqualTo("You clicked: Cancel");
    }

    @Test(description = "JS prompt answered with text")
    public void jsPrompt() {
        JsAlertsPage alerts = openAlertsPage();
        alerts.trigger(PROMPT);
        alerts.typeIntoPrompt("Test Input");
        alerts.accept();

        assertThat(alerts.resultText()).isEqualTo("You entered: Test Input");
    }
}
