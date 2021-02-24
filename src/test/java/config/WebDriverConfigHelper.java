package config;

import org.aeonbits.owner.ConfigFactory;

public class WebDriverConfigHelper {

    private static WebDriverConfig getConfig() {
        if (System.getProperty("environment") == null) System.setProperty("environment", "localWeb"); // test, preprod

        return ConfigFactory.newInstance().create(WebDriverConfig.class, System.getProperties());
    }

    public static String getWebRemoteDriver() {
        return "https://" + getConfig().remoteDriverUser() + ":" +
                getConfig().remoteDriverPassword() + "@" +
                getRemoteDriverUrl() + "/wd/hub";
    }

    public static String getRemoteDriverUrl() {
        return getConfig().remoteDriverUrl();
    }

    public static boolean isRemoteWebDriver() {
        return !getConfig().remoteDriverUrl().equals("");
    }

}
