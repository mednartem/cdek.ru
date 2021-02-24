package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:${environment}.properties"
})
public interface WebDriverConfig extends Config {

    @Key("remote.driver.url")
    String remoteDriverUrl();

    @Key("remote.driver.user")
    String remoteDriverUser();

    @Key("remote.driver.password")
    String remoteDriverPassword();

    @Key("video")
    Boolean isVideo();

}
