package com.novels;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple CommonApp.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        Duration idleTimeout = Duration.ofMillis(60000L);
        System.out.println(idleTimeout.toMillis());
    }
}
