package tests;


import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static config.WebDriverConfigHelper.getWebRemoteDriver;
import static config.WebDriverConfigHelper.isRemoteWebDriver;
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
        if (isRemoteWebDriver())
            Configuration.remote = getWebRemoteDriver();
    }

    @AfterEach
    @Step("Attachments")
    public void afterEach() {
        if (isRemoteWebDriver()) {
            attachScreenshot("Last screenshot");
            attachPageSource();
            attachAsText("Browser console logs", getConsoleLogs());
            attachVideo();
        }
        closeWebDriver();
    }
}
