package com.krieger.hoeffner.e2e.support.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.krieger.hoeffner.e2e.support.TestConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Scenario scoped, so separate driver is used per testcase. Ensures test independence,
 * and supports distributed testing with frameworks such as Selenium Hub or Selenoid.
 * Note that Singletons with dependency on WebDriver should use WebDriverSupport, or
 * the {@literal Provider<WebDriver>} directly.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class WebDriverProvider implements Provider<WebDriver> {
    private final TestConfig config;
    private final DriverService service;
    private final Capabilities options;
    private WebDriver webDriver;

    @Override
    public WebDriver get() {
        log.info("Creating WebDriver");
        webDriver = new RemoteWebDriver(service.getUrl(), options);

        webDriver.manage().window().setSize(new Dimension(1280, 800));

        webDriver.manage().timeouts()
                .implicitlyWait(config.webDriverImplicitWaitSeconds(), SECONDS)
                .pageLoadTimeout(config.webDriverPageLoadTimeoutSeconds(), SECONDS);

        return webDriver;
    }

    public Optional<WebDriver> getOptional() {
        return Optional.ofNullable(webDriver);
    }
}

