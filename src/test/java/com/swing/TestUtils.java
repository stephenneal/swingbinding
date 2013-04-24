package com.swing;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.Assert;

public class TestUtils {

    private TestUtils() {
    }

    /**
     * Get the value of a private field (useful where a public getter does not exist). Field can be in the class specified or a superclass.
     */
    public static Object getInternalState(Object instance, String fieldName) {
        try {
            Field f = getField(instance, fieldName);
            f.setAccessible(true);
            Object value = f.get(instance);
            f.setAccessible(false);
            return value;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the value of a private field (useful where a public setter does not exist). Field can be in the class specified or a superclass.
     */
    public static <T> T setInternalState(Object instance, String fieldName, T value) {
        try {
            Field f = getField(instance, fieldName);
            f.setAccessible(true);
            f.set(instance, value);
            f.setAccessible(false);
            return value;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * Get a field from an object instance. If not found in the concrete class it searches up the class heirarchy.
     */
    private static Field getField(Object instance, String fieldName) throws NoSuchFieldException {
        Class<?> c = instance.getClass();
        try {
            return c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // Search any super-classes, if not found propagate the original NoSuchFieldException
            c = c.getSuperclass();
            while (c != null) {
                try {
                    return c.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e2) {
                    c = c.getSuperclass();
                }
            }
            throw e;
        }
    }
}
