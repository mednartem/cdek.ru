package tests;


import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static config.Constants.IS_VIDEO_ON;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.configureSelenide;

@ExtendWith(AllureJunit5.class)
public class TestBase {
    @BeforeAll
    static void setup() {
        configureSelenide();


//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("enableVNC", true);
//        capabilities.setCapability("enableVideo", true);
//
//        Configuration.baseUrl = "https://www.cdek.ru/ru/";
//        Configuration.browserSize = "1600x1200";
//        Configuration.browserCapabilities = capabilities;
//        if (IS_REMOTE) {
//            Configuration.remote = REMOTE_DRIVER_URL;
//            Configuration.screenshots = true;
//            Configuration.savePageSource = true;
//        }
    }

    @AfterEach
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        if (IS_VIDEO_ON) attachVideo(getSessionId());
        closeWebDriver();

    }
}
