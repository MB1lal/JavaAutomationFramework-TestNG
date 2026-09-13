package com.automation.pages;

import com.automation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;
import java.util.function.Function;

/**
 * Landing page of the-internet.herokuapp.com. Knows how to open itself
 * and navigate to every page the tests cover.
 */
public class HomePage extends BasePage {

    private final String baseUrl = ConfigReader.getInstance().get("heroku.url");

    @FindBy(linkText = "Form Authentication")
    private WebElement formAuthenticationLink;

    @FindBy(linkText = "Checkboxes")
    private WebElement checkboxesLink;

    @FindBy(linkText = "Dropdown")
    private WebElement dropdownLink;

    @FindBy(linkText = "Dynamic Loading")
    private WebElement dynamicLoadingLink;

    @FindBy(linkText = "File Download")
    private WebElement fileDownloadLink;

    @FindBy(linkText = "File Upload")
    private WebElement fileUploadLink;

    @FindBy(linkText = "Frames")
    private WebElement framesLink;

    @FindBy(linkText = "Hovers")
    private WebElement hoversLink;

    @FindBy(linkText = "JavaScript Alerts")
    private WebElement jsAlertsLink;

    @FindBy(linkText = "Multiple Windows")
    private WebElement windowsLink;

    @FindBy(linkText = "Notification Messages")
    private WebElement notificationsLink;

    private final Map<String, Function<HomePage, BasePage>> pagesByName = Map.ofEntries(
            Map.entry("form authentication", page -> { click(formAuthenticationLink); return new LoginPage(driver); }),
            Map.entry("checkboxes", page -> { click(checkboxesLink); return new CheckboxesPage(driver); }),
            Map.entry("dropdown", page -> { click(dropdownLink); return new DropdownPage(driver); }),
            Map.entry("dynamic loading", page -> { click(dynamicLoadingLink); return new DynamicLoadingHomePage(driver); }),
            Map.entry("file download", page -> { click(fileDownloadLink); return new FileDownloadPage(driver); }),
            Map.entry("file upload", page -> { click(fileUploadLink); return new FileUploadPage(driver); }),
            Map.entry("frames", page -> { click(framesLink); return new FramesPage(driver); }),
            Map.entry("hovers", page -> { click(hoversLink); return new HoversPage(driver); }),
            Map.entry("javascript alerts", page -> { click(jsAlertsLink); return new JsAlertsPage(driver); }),
            Map.entry("multiple windows", page -> { click(windowsLink); return new WindowsPage(driver); }),
            Map.entry("notification messages", page -> { click(notificationsLink); return new NotificationPage(driver); })
    );

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        open(baseUrl);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePage> T goTo(String pageName) {
        Function<HomePage, BasePage> navigation =
                pagesByName.get(pageName.toLowerCase().trim());
        if (navigation == null) {
            throw new IllegalArgumentException("Unknown page: " + pageName);
        }
        return (T) navigation.apply(this);
    }
}
