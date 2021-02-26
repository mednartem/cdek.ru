package driver;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static config.Constants.*;

public class CustomWebDriver implements WebDriverProvider {


    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull DesiredCapabilities capabilities) {
        addListener("AllureSelenide", new AllureSelenide().screenshots(false).savePageSource(false));
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        capabilities.setCapability("enableVNC", true);

        if (IS_VIDEO_ON) {
            capabilities.setCapability("enableVideo", true);
            capabilities.setCapability("videoFrameRate", 24);
        }

        if (IS_REMOTE) {
            switch (BROWSER) {
                case "chrome":
                    capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                    WebDriverManager.chromedriver().setup();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    // todo
                    break;
            }
            return getRemoteWebDriver(capabilities);
        } else {
            return getLocalChromeDriver();
        }
    }

    private ChromeDriver getLocalChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(getChromeOptions());
    }

    private WebDriver getRemoteWebDriver(DesiredCapabilities capabilities) {
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(getRemoteWebDriverUrl(), capabilities);
        remoteWebDriver.setFileDetector(new LocalFileDetector());

        return remoteWebDriver;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

//        if (!getWebMobileDevice().equals("")) {
//            Map<String, Object> mobileDevice = new HashMap<>();
//            mobileDevice.put("deviceName", getWebMobileDevice());
//            chromeOptions.setExperimentalOption("mobileEmulation", mobileDevice);
//        }
//        chromeOptions.addArguments("--window-size=" + getWebBrowserScreenResolution());
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-gpu");

        return chromeOptions;
    }

    private URL getRemoteWebDriverUrl() {
        try {
            return new URL(REMOTE_DRIVER_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
