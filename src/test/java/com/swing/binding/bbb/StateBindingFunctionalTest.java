package com.swing.binding.bbb;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.jdesktop.beansbinding.Binding;
import org.junit.Test;

import com.swing.binding.TestBean;

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
     * Test {@link StateBinding#editable(Object, String, JTextComponent)} for a {@link JTextField} where the bean
     * property is a non-primitive {@link Boolean}.
     * 
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    @Test
    public void testJTextFieldEditableObject() throws InterruptedException, InvocationTargetException {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.editable(bean, "state", textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getState());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });

        // Update the bean value
        bean.setState(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEditable(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(Boolean.TRUE, bean.getState());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setState(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });
        // Set a new value on the bean property
        bean.setState(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });
        // Set the bean value to null
        bean.setState(null);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });
        // Update the component with a value (binding is read only so bean should not be updated)
        textField.setEditable(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getState());
            }
        });
    }

    /**
     * Test {@link StateBinding#editable(Object, String, JComponent)} for a {@link JTextField} where the bean property
     * is a primitive.
     * 
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    @Test
    public void testJTextFieldEditablePrimitive() throws InterruptedException, InvocationTargetException {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.editable(bean, "statePrimitive",
                        textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isStatePrimitive());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });

        // Update the bean value
        bean.setStatePrimitive(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEditable(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, bean.isStatePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setStatePrimitive(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });
        // Set a new value on the bean property
        bean.setStatePrimitive(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });
    }

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
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.enabled(bean, "state", textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getState());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });

        // Update the bean value
        bean.setState(true);
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
                assertEquals(Boolean.TRUE, bean.getState());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setState(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Set a new value on the bean property
        bean.setState(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
        // Set the bean value to null
        bean.setState(null);
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
                assertEquals(null, bean.getState());
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
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.enabled(bean, "statePrimitive",
                        textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isStatePrimitive());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });

        // Update the bean value
        bean.setStatePrimitive(true);
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
                assertEquals(true, bean.isStatePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setStatePrimitive(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Set a new value on the bean property
        bean.setStatePrimitive(true);
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
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.visible(bean, "state", textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getState());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });

        // Update the bean value
        bean.setState(true);
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
                assertEquals(Boolean.TRUE, bean.getState());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setState(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Set a new value on the bean property
        bean.setState(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
        // Set the bean value to null
        bean.setState(null);
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
                assertEquals(null, bean.getState());
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
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.visible(bean, "statePrimitive",
                        textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isStatePrimitive());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });

        // Update the bean value
        bean.setStatePrimitive(true);
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
                assertEquals(true, bean.isStatePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setStatePrimitive(false);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Set a new value on the bean property
        bean.setStatePrimitive(true);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
    }

}
