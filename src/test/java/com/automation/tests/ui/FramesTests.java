package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.FramesPage;
import com.automation.pages.HomePage;
import com.automation.pages.IframeEditorPage;
import com.automation.pages.NestedFramesPage;
import org.testng.annotations.Test;

import static com.automation.pages.NestedFramesPage.Frame.BOTTOM;
import static com.automation.pages.NestedFramesPage.Frame.TOP_LEFT;
import static com.automation.pages.NestedFramesPage.Frame.TOP_MIDDLE;
import static com.automation.pages.NestedFramesPage.Frame.TOP_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code frames.feature}.
 */
@Test(groups = "ui")
public class FramesTests extends UiBaseTest {

    @Test(description = "Each nested frame shows its own text")
    public void nestedFrames() {
        FramesPage frames = new HomePage(driver()).open().goTo("frames");
        NestedFramesPage nested = frames.openNestedFrames();

        assertThat(nested.textOfFrame(TOP_LEFT)).isEqualTo("LEFT");
        assertThat(nested.textOfFrame(TOP_MIDDLE)).isEqualTo("MIDDLE");
        assertThat(nested.textOfFrame(TOP_RIGHT)).isEqualTo("RIGHT");
        assertThat(nested.textOfFrame(BOTTOM)).isEqualTo("BOTTOM");
    }

    @Test(description = "Text can be written and read back in the iframe editor")
    public void writeInIframe() {
        FramesPage frames = new HomePage(driver()).open().goTo("frames");
        IframeEditorPage editor = frames.openIframe();
        String text = "Hello from TestNG";

        editor.write(text);

        assertThat(editor.content()).contains(text);
    }
}
