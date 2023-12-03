package com.krieger.hoeffner.e2e.steps;

import com.google.inject.Inject;
import com.krieger.hoeffner.e2e.support.guice.WebDriverProvider;
import com.krieger.hoeffner.e2e.support.services.InfrastructureService;
import com.krieger.hoeffner.e2e.support.services.KriegerWebAppService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TakesScreenshot;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.OutputType.BYTES;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class Hooks {
    private enum Service {
        KRIEGER_WEBAPP_HOST,
    }

    /**
     * Static so result is cached and invoked only once per agent.
     */
    private static final Map<Service, Boolean> serviceStates = new HashMap<>();

    private final WebDriverProvider webDriverProvider;
    private final KriegerWebAppService kriegerWebAppService;

    @Before(order = 0)
    public void before() {
        verifySiriusWebAppHostHealthy();
    }

    @After
    public void after(Scenario scenario) {
        screenshotOnFailure(scenario);
        closeWebDriver();
    }

    private void verifySiriusWebAppHostHealthy() {
        checkService(kriegerWebAppService);
    }

    /**
     * Cache service check results. On first use for a service, throws any exception the check throws, or a generic
     * exception if it returns false. Subsequent calls will immediately succeed or fail with a generic exception.
     */
    private void checkService(InfrastructureService target) {
        try {
            log.info("Verifying " + Service.KRIEGER_WEBAPP_HOST + " is available...");
            if (!serviceStates.computeIfAbsent(Service.KRIEGER_WEBAPP_HOST, svc -> target.isHealthy())) {
                throw new RuntimeException("Service '" + Service.KRIEGER_WEBAPP_HOST + "' is unavailable. See logs");
            }
        } catch (Exception x) {
            serviceStates.put(Service.KRIEGER_WEBAPP_HOST, false);
            throw x;
        }
    }

    private void screenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            webDriverProvider.getOptional().ifPresent(driver -> {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(BYTES);
                scenario.attach(screenshot, "image/png", "failure-screenshot");
            });
        }
    }

    /**
     * Quit the WebDriver (we always use a remote) after each scenario (not just close). See
     * WebDriver initializer (i.e. WebDriverModule for the shutdown hook which handles quitting
     * the local DriverService (if running).
     */
    public void closeWebDriver() {
        webDriverProvider.getOptional().ifPresent(driver -> {
            log.info("Closing WebDriver");
            driver.quit();
        });
    }

}

