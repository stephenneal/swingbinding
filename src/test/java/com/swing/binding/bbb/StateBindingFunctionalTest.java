package com.swing.binding.bbb;

import static org.junit.Assert.assertEquals;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Property;
import org.junit.Test;

import com.swing.TestUtils;
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
     * Test {@link StateBinding#editable(Object, Property, JTextComponent)} for a {@link JTextField} where the bean
     * property is a non-primitive {@link Boolean}.
     */
    @Test
    public void testEditableJTextField() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("state");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.editable(bean, bP, textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getState());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });

        // Update the bean value
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEditable(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(Boolean.TRUE, bean.getState());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setState(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });
        // Set a new value on the bean property
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });
        // Set the bean value to null
        bean.setState(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });
        // Update the component with a value (binding is read only so bean should not be updated)
        textField.setEditable(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getState());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link StateBinding#editable(Object, Property, JTextComponent)} for a {@link JTextField} where the bean
     * property is a primitive.
     */
    @Test
    public void testEditablePrimitiveJTextField() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("statePrimitive");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.editable(bean, bP, textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isStatePrimitive());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });

        // Update the bean value
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEditable(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, bean.isStatePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setStatePrimitive(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEditable());
            }
        });
        // Set a new value on the bean property
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEditable());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link StateBinding#editable(Object, Property, JComboBox)} where the bean property is a non-primitive
     * {@link Boolean}.
     */
    @Test
    public void testEditableJComboBox() {
        // Setup
        final TestBean bean = new TestBean();
        final JComboBox component = new JComboBox();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("state");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.editable(bean, bP, component);
        binding.bind();

        // Test
        assertEquals(null, bean.getState());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, component.isEditable());
            }
        });

        // Update the bean value
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, component.isEditable());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        component.setEditable(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(Boolean.TRUE, bean.getState());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setState(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, component.isEditable());
            }
        });
        // Set a new value on the bean property
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, component.isEditable());
            }
        });
        // Set the bean value to null
        bean.setState(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, component.isEditable());
            }
        });
        // Update the component with a value (binding is read only so bean should not be updated)
        component.setEditable(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getState());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link StateBinding#editable(Object, Property, JComboBox)} where the bean property is a primitive.
     */
    @Test
    public void testEditablePrimitiveJComboBox() {
        // Setup
        final TestBean bean = new TestBean();
        final JComboBox component = new JComboBox();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("statePrimitive");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.editable(bean, bP, component);
        binding.bind();

        // Test
        assertEquals(false, bean.isStatePrimitive());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, component.isEditable());
            }
        });

        // Update the bean value
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, component.isEditable());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        component.setEditable(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, bean.isStatePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setStatePrimitive(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, component.isEditable());
            }
        });
        // Set a new value on the bean property
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, component.isEditable());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link StateBinding#enabled(Object, Property, JComponent)} for a {@link JTextField} where the bean property
     * is a non-primitive {@link Boolean}.
     */
    @Test
    public void testEnabledJTextField() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("state");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.enabled(bean, bP, textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getState());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });

        // Update the bean value
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEnabled(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(Boolean.TRUE, bean.getState());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setState(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Set a new value on the bean property
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
        // Set the bean value to null
        bean.setState(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Update the component with a value (binding is read only so bean should not be updated)
        textField.setEnabled(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getState());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link StateBinding#enabled(Object, Property, JComponent)} for a {@link JTextField} where the bean property
     * is a primitive.
     */
    @Test
    public void testEnabledPrimitiveJTextField() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("statePrimitive");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.enabled(bean, bP, textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isStatePrimitive());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });

        // Update the bean value
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setEnabled(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, bean.isStatePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setStatePrimitive(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isEnabled());
            }
        });
        // Set a new value on the bean property
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isEnabled());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link StateBinding#visible(Object, Property, JComponent)} for a {@link JTextField} where the bean property
     * is a non-primitive {@link Boolean}.
     */
    @Test
    public void testVisibleJTextField() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("state");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.visible(bean, bP, textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getState());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });

        // Update the bean value
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setVisible(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(Boolean.TRUE, bean.getState());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setState(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Set a new value on the bean property
        bean.setState(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
        // Set the bean value to null
        bean.setState(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Update the component with a value (binding is read only so bean should not be updated)
        textField.setVisible(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getState());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link StateBinding#visible(Object, Property, JComponent)} for a {@link JTextField} where the bean property
     * is a primitive.
     */
    @Test
    public void testVisiblePrimitiveJTextField() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Property<TestBean, Boolean> bP = BeanProperty.create("statePrimitive");
        Binding<TestBean, Boolean, JComponent, Boolean> binding = StateBinding.visible(bean, bP, textField);
        binding.bind();

        // Test
        assertEquals(false, bean.isStatePrimitive());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });

        // Update the bean value
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });
        // Reverse the state via the component (binding is read only so bean should not be updated)
        textField.setVisible(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, bean.isStatePrimitive());
            }
        });
        // Set the same value on the bean property, the component will not be updated (same value means no property
        // change)
        bean.setStatePrimitive(false);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, textField.isVisible());
            }
        });
        // Set a new value on the bean property
        bean.setStatePrimitive(true);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, textField.isVisible());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

}
