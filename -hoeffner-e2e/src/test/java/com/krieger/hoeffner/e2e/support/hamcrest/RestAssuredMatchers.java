package com.krieger.hoeffner.e2e.support.hamcrest;

import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.is;

/**
 * A hamcrest status matcher, with a better description on failure.
 */
public class RestAssuredMatchers {
    /**
     * A hamcrest status matcher, with a better description on failure.
     *
     * @param description e.g. "Fetch Alarms", "Check API Service health"
     * @param expected    status code to match
     */
    public static Matcher<Integer> expectedStatus(String description, int expected) {
        return describedAs("%0 to %1,", is(expected), expected, description);
    }
}

