package com.swing;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.Assert;

public class TestUtils {

    private TestUtils() {
    }

    /**
     * Call {@link SwingUtilities#invokeAndWait(Runnable)} and interpret the result.
     */
    public static final void invokeAndWait(Runnable r) {
        try {
            SwingUtilities.invokeAndWait(r);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            Throwable report = e.getCause() != null ? e.getCause() : e;
            if (report instanceof AssertionError) {
                Assert.fail(report.getMessage());
                return;
            }
            if (report instanceof RuntimeException) {
                throw (RuntimeException) report;
            }
            throw new RuntimeException(report);
        }
    }

}
