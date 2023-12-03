package com.krieger.hoeffner.e2e.support;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.krieger.hoeffner.e2e.steps",
        plugin = { "de.monochromata.cucumber.report.PrettyReports:target/cucumber" },
        stepNotifications = true)
public class TestRunner {
}
