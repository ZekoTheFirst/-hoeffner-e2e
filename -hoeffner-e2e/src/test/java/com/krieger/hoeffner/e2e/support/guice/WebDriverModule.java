package com.krieger.hoeffner.e2e.support.guice;

import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.krieger.hoeffner.e2e.support.TestConfig;
import com.krieger.hoeffner.e2e.support.WebDriverSupport;
import io.cucumber.guice.ScenarioScoped;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.service.DriverService;

import javax.inject.Singleton;
import java.io.IOException;

import static org.openqa.selenium.remote.BrowserType.CHROME;
import static org.openqa.selenium.remote.BrowserType.FIREFOX;

@Slf4j
public class WebDriverModule extends PrivateModule {

    @Override
    protected void configure() {
        bind(WebDriverSupport.class).in(Singleton.class);
        expose(WebDriverSupport.class);

        bind(WebDriverProvider.class).in(ScenarioScoped.class);
        expose(WebDriverProvider.class);
        bind(WebDriver.class).toProvider(WebDriverProvider.class).in(ScenarioScoped.class);
        expose(WebDriver.class);
    }

    @Provides
    @Singleton
    public DriverService driverService(TestConfig config) throws IOException {
        final DriverService service;

        final String browser = config.browser().toLowerCase();
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                service = new ChromeDriverService.Builder().build();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                service = new GeckoDriverService.Builder().build();
                break;
            default:
                throw new IllegalStateException("Unrecognized browser in config: " + browser);
        }

        log.info("Starting DriverService: " + service.getClass().getSimpleName());
        service.start();

        // Cucumber does not trigger @AfterClass JUnit handlers
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("Stopping DriverService");
                service.stop();
            } catch (Exception x) {
                log.error("Error stopping DriverService", x);
            }
        }));

        return service;
    }

    @Provides
    @Singleton
    public Capabilities webDriverCapabilities(TestConfig config) {
        final String browser = config.browser().toLowerCase();
        final String userAgent = config.userAgent();
        switch (browser) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions()
                        .setHeadless(config.headless());

                if (userAgent != null) {
                    chromeOptions.addArguments("-user-agent=" + userAgent);
                }

                return chromeOptions;

            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions()
                        .setHeadless(config.headless());

                if (userAgent != null) {
                    var profile = new FirefoxProfile();
                    profile.setPreference("general.useragent.override", userAgent);
                    firefoxOptions.setProfile(profile);
                }

                return firefoxOptions;

            default:
                throw new IllegalStateException("Unrecognized browser in config: " + browser);
        }
    }
}

