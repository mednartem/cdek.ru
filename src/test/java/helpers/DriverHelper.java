package helpers;

import com.codeborne.selenide.Configuration;
import driver.CustomWebDriver;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class DriverHelper {

    public static void configureSelenide() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        Configuration.browser = CustomWebDriver.class.getName();
        Configuration.baseUrl = "https://www.cdek.ru/ru/";
        Configuration.browserSize = "1600x1200";
        Configuration.pageLoadTimeout = 30000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.headless = true;
        Configuration.clickViaJs = true;
        Configuration.fastSetValue = true;
        Configuration.timeout = 10000;
    }
}
