package com.krieger.hoeffner.e2e.support;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.openqa.selenium.remote.BrowserType;

import static com.krieger.hoeffner.e2e.support.guice.ConfigModule.ENV_PROPERTY_FILE_TEMPLATE;

@Config.LoadPolicy(Config.LoadType.MERGE) // first source with a given property takes precedence
@Config.Sources({
        "system:env",
        "system:properties",
        "file:test-override.properties",
        // `environment` value set within ConfigModule
        "classpath:" + ENV_PROPERTY_FILE_TEMPLATE })
public interface TestConfig extends Config, Accessible {
    @DefaultValue(BrowserType.CHROME)
    String browser();
    String userAgent();
    boolean headless();
    String baseUrl();

    @Key("users.%s.email")
    String email(String id);

    String emailServiceKey();
    String emailServiceUrl();

    int webDriverImplicitWaitSeconds();
    int webDriverPageLoadTimeoutSeconds();

    default String getUrl(String uri) {
        return baseUrl() + uri;
    }
}
