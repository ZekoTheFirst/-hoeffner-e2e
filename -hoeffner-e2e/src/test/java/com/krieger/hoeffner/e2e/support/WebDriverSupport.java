package com.krieger.hoeffner.e2e.support;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;

import javax.inject.Provider;

/**
 * Access to the current WebDriver as well as utility methods.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class WebDriverSupport {
    @Getter
    private final TestConfig config;
    private final Provider<WebDriver> wdProvider;

    public WebDriver getWebDriver() {
        return wdProvider.get();
    }

}