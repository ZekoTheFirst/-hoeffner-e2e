package com.krieger.hoeffner.e2e.support.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.krieger.hoeffner.e2e.support.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;

import javax.inject.Singleton;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

@Slf4j
public class ConfigModule extends AbstractModule {

    public static final String ENV_PROPERTY_NAME = "environment";
    public static final String ENV_PROPERTY_FILE_TEMPLATE = "environments/test-${" + ENV_PROPERTY_NAME + "}.properties";
    private static final String DEFAULT_ENVIRONMENT = "qa";

    @Provides
    @Singleton
    private TestConfig createConfig() {
        /**
         * Set `environment` value used by @Sources annotation (see TestConfig) to select properties file.
         */
        final String env = System.getProperty(ENV_PROPERTY_NAME, DEFAULT_ENVIRONMENT);
        ConfigFactory.setProperty(ENV_PROPERTY_NAME, env);
        log.info("Loading '{}' environment configuration", env);

        verifyEnvConfig();
        final TestConfig config = ConfigFactory.create(TestConfig.class);

        if (log.isDebugEnabled()) {
            StringWriter writer = new StringWriter();
            config.list(new PrintWriter(writer));
            log.info("Loaded configuration: {}", writer);
        }
        return config;
    }

    /**
     * Owner does not require any given source, so ensure here the config file can be found.
     */
    private void verifyEnvConfig() {
        // complex sanity check... because owner is not very helpful
        String env = ConfigFactory.getProperty(ENV_PROPERTY_NAME);
        String resourcePath = "/" + ENV_PROPERTY_FILE_TEMPLATE.replace("${" + ENV_PROPERTY_NAME + "}", env);

        URL propertiesUrl = getClass().getResource(resourcePath);
        if (propertiesUrl == null) {
            throw new RuntimeException("Could not find env properties file: " + resourcePath);
        }
    }
}
