package config;

import static config.WebDriverConfigHelper.*;

public class Constants {
    public static final Boolean IS_REMOTE = isRemoteWebDriver();
    public static final Boolean IS_VIDEO_ON = isVideo();
    public static final String REMOTE_DRIVER_URL = getWebRemoteDriver();
    public static final String BROWSER = getBrowserName();
}
