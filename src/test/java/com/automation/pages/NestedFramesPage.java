package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The nested-frames page is a frameset two levels deep: a top frameset
 * (left / middle / right) over a bottom frame.
 */
public class NestedFramesPage extends BasePage {

    @FindBy(name = "frame-top")
    private WebElement topFrame;

    @FindBy(name = "frame-left")
    private WebElement leftFrame;

    @FindBy(name = "frame-middle")
    private WebElement middleFrame;

    @FindBy(name = "frame-right")
    private WebElement rightFrame;

    @FindBy(name = "frame-bottom")
    private WebElement bottomFrame;

    @FindBy(css = "body")
    private WebElement frameBody;

    public NestedFramesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Reads the text of one frame and switches back to the top level,
     * so calls can be chained without worrying about frame context.
     */
    public String textOfFrame(Frame frame) {
        try {
            if (frame != Frame.BOTTOM) {
                driver.switchTo().frame(topFrame);
            }
            driver.switchTo().frame(switch (frame) {
                case TOP_LEFT -> leftFrame;
                case TOP_MIDDLE -> middleFrame;
                case TOP_RIGHT -> rightFrame;
                case BOTTOM -> bottomFrame;
            });
            return frameBody.getText().trim();
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    public enum Frame {
        TOP_LEFT, TOP_MIDDLE, TOP_RIGHT, BOTTOM
    }
}
