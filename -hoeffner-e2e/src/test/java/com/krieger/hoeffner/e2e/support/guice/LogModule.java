package com.krieger.hoeffner.e2e.support.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Bit of a hack to send the java.util.logging (JUL) output to Slf4J. Since Guice is
 * guaranteed to run (from cli, maven, test-runner in IDE, etc) even though
 * this class does not actually inject anything via Guice, it is useful to
 * configure JUL. This LogModule was considered cleaner than adding a static
 * method somewhere.
 * <p>
 * <br/>Other options require command line settings, which becomes cumbersome.
 * <p>
 * <br/>Even a Cucumber plugin would not always run, e.g. when users launch
 * from an IDE.
 * <p>
 * <br/>Note that ChromeDriver (as a non-java binary) will still send
 * output to stderr.
 */
@Slf4j
public class LogModule implements Module {

    @Override
    public void configure(Binder binder) {
        log.info("Installing SLF4J Bridge Handler");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
