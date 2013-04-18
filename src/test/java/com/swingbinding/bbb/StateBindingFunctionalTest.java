package com.swingbinding.bbb;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jdesktop.beansbinding.Binding;
import org.junit.Test;

import com.swingbinding.TestBean;

/**
 * Tests the functionality of {@link StateBinding}.
 * <p>
 * This does not test the class in isolation (as per a unit test), it tests with real bindings (BetterBeansBinding).
 * </p>
 * 
 * @author Stephen Neal
 * @since 18/04/2013
 */
public class StateBindingFunctionalTest {

    /**
     * Test {@link StateBinding#enabled(Object, String, JComponent)} for a {@link JTextField} where the bean property is
     * a non-primitive {@link Boolean}.
     * 
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    @Test
    public void testJTextFieldEnabledObject() throws InterruptedException, InvocationTargetException {
        // Setup
        final TestBean bean = TestBean.newInstance();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.enabled(bean, "enabled", textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getEnabled());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });

        // Update the bean value
        bean.setEnabled(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEnabled(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(Boolean.TRUE, bean.getEnabled());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setEnabled(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Set a new value on the bean property
        bean.setEnabled(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
        // Set the bean value to null
        bean.setEnabled(null);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Update the component with a value (binding is read only so bean should not be updated)
        textField.setEnabled(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getEnabled());
            }
        });
    }

    /**
     * Test {@link StateBinding#enabled(Object, String, JComponent)} for a {@link JTextField} where the bean property is
     * a primitive.
     * 
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    @Test
    public void testJTextFieldEnabledPrimitive() throws InterruptedException, InvocationTargetException {
        // Setup
        final TestBean bean = TestBean.newInstance();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.enabled(bean, "enabledPrimitive",
                        textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isEnabledPrimitive());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });

        // Update the bean value
        bean.setEnabledPrimitive(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEnabled(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, bean.isEnabledPrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setEnabledPrimitive(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Set a new value on the bean property
        bean.setEnabledPrimitive(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
    }

    /**
     * Test {@link StateBinding#visible(Object, String, JComponent)} for a {@link JTextField} where the bean property is
     * a non-primitive {@link Boolean}.
     * 
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    @Test
    public void testJTextFieldVisibleObject() throws InterruptedException, InvocationTargetException {
        // Setup
        final TestBean bean = TestBean.newInstance();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.visible(bean, "visible", textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getVisible());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });

        // Update the bean value
        bean.setVisible(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setVisible(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(Boolean.TRUE, bean.getVisible());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setVisible(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Set a new value on the bean property
        bean.setVisible(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
        // Set the bean value to null
        bean.setVisible(null);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Update the component with a value (binding is read only so bean should not be updated)
        textField.setVisible(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getVisible());
            }
        });
    }

    /**
     * Test {@link StateBinding#visible(Object, String, JComponent)} for a {@link JTextField} where the bean property is
     * a primitive.
     * 
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    @Test
    public void testJTextFieldVisiblePrimitive() throws InterruptedException, InvocationTargetException {
        // Setup
        final TestBean bean = TestBean.newInstance();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.visible(bean, "visiblePrimitive",
                        textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isVisiblePrimitive());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });

        // Update the bean value
        bean.setVisiblePrimitive(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setVisible(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, bean.isVisiblePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setVisiblePrimitive(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Set a new value on the bean property
        bean.setVisiblePrimitive(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
    }

}
