package tests;


import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;

public class TestBase {
    @BeforeAll
    static void setup() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.baseUrl = "https://www.cdek.ru/ru/";
        Configuration.browserSize = "1600x1200";
        Configuration.browserCapabilities = capabilities;
        if (System.getProperty("remote.browser.url") != null)
            Configuration.remote = "https://user1:1234@" + System.getProperty("remote.browser.url") + ":4444/wd/hub/";
    }

    @AfterEach
    @Step("Attachments")
    public void afterEach() {
        if (System.getProperty("remote.browser.url") != null) {
            attachScreenshot("Last screenshot");
            attachPageSource();
            attachAsText("Browser console logs", getConsoleLogs());
            attachVideo();
        }
        closeWebDriver();
    }
}
