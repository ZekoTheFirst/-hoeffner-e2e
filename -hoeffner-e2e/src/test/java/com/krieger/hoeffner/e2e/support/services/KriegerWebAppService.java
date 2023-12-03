package com.krieger.hoeffner.e2e.support.services;

import com.google.inject.Inject;
import com.krieger.hoeffner.e2e.support.TestConfig;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static com.krieger.hoeffner.e2e.support.hamcrest.RestAssuredMatchers.expectedStatus;

/**
 * Simple class to implement a health-check for pre-test validation.
 * Except for this, all web-app communication should be driven through Selenium.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class KriegerWebAppService implements InfrastructureService {
    private final TestConfig config;

    /**
     * Returns true if host is up and serving the index page (authorization unnecessary) or throws
     * an exception if not available.
     */
    @Override
    @SneakyThrows
    public boolean isHealthy() {
        RestAssured.when()
                // TODO: use a health check endpoint
                .get(config.baseUrl()) // use full url, to not set static baseURI for webapp
                .then()
                .statusCode(expectedStatus("Check WebApp Service health", 200));
        return true;
    }
}

