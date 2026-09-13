package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.DynamicLoadingExamplePage;
import com.automation.pages.DynamicLoadingHomePage;
import com.automation.pages.HomePage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code dynamic-loading.feature}.
 */
@Test(groups = "ui")
public class DynamicLoadingTests extends UiBaseTest {

    @DataProvider(name = "examples")
    public Object[][] examples() {
        return new Object[][]{{1}, {2}};
    }

    @Test(description = "Hidden element loads with 'Hello World!' text", dataProvider = "examples")
    public void loadedElementAppears(int example) {
        DynamicLoadingHomePage dynamicLoading =
                new HomePage(driver()).open().goTo("dynamic loading");
        DynamicLoadingExamplePage page = dynamicLoading.openExample(example);
        page.start();

        assertThat(page.waitForLoadedText()).contains("Hello World!");
    }
}
