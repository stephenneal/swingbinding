package com.swing.binding.bbb;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.Binding;
import org.junit.Test;

import com.swing.test.TestUtils;
import com.swing.binding.TestBean;
import com.swing.binding.TestBean.Properties;

/**
 * Tests the functionality of {@link TextBinding}.
 * <p>
 * This does not test the class in isolation (as per a unit test), it tests with real bindings (BetterBeansBinding).
 * </p>
 * 
 * @author Stephen Neal
 * @since 18/07/2011
 */
public class TextBindingFunctionalTest {

    /**
     * Test {@link TextBinding#text(Object, org.jdesktop.beansbinding.Property, javax.swing.text.JTextComponent)} for a
     * {@link JTextField} and property of type {@link String}.
     */
    @Test
    public void testTextJTextFieldString() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, String, JComponent, String> binding = TextBinding.text(bean, Properties.STRING, textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getString());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Update the bean value
        final String value = "value";
        bean.setString(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value, textField.getText());
            }
        });
        // Clear the text field
        textField.setText(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", bean.getString());
            }
        });
        // Update the text field with a value
        textField.setText(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value, bean.getString());
            }
        });
        // Clear the bean value
        bean.setString(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link TextBinding#text(Object, org.jdesktop.beansbinding.Property, javax.swing.text.JTextComponent)} for a
     * {@link JTextField} and property of type {@link Integer}.
     */
    @Test
    public void testTextJTextFieldInteger() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Integer, JComponent, String> binding = TextBinding.text(bean, Properties.INTEGER, textField);
        binding.bind();

        // Test
        assertEquals(null, bean.getIntegr());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Update the bean value
        final Integer value = 10;
        bean.setIntegr(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value.toString(), textField.getText());
            }
        });
        // Clear the text field
        textField.setText(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getIntegr());
            }
        });
        // Update the text field with a value
        textField.setText(value.toString());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value, bean.getIntegr());
            }
        });
        // Clear the bean value
        bean.setIntegr(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link TextBinding#text(Object, org.jdesktop.beansbinding.Property, javax.swing.text.JTextComponent)} for a
     * {@link JTextField} and property of type {@link Date}.
     */
    @Test
    public void testTextJTextFieldDate() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Bind
        Binding<TestBean, Date, JComponent, String> binding = TextBinding.text(bean, Properties.DATE, textField);
        binding.bind();
        // Default format when a format is not specified
        final DateFormat defaultFormat = DateFormat.getDateInstance();

        // Test
        assertEquals(null, bean.getDate());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Update the bean value
        final Date value = Calendar.getInstance().getTime();
        bean.setDate(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(defaultFormat.format(value), textField.getText());
            }
        });
        // Clear the text field
        textField.setText(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getDate());
            }
        });
        // Update the text field with a value
        textField.setText(defaultFormat.format(value));
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {
                    assertEquals(defaultFormat.parse(textField.getText()), bean.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Clear the bean value
        bean.setDate(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link TextBinding#text(Object, org.jdesktop.beansbinding.Property, JComponent, java.text.DateFormat)} for a
     * {@link JTextField}.
     */
    @Test
    public void testTextDateFormatJTextField() {
        // Setup
        final TestBean bean = new TestBean();
        final JTextField textField = new JTextField();

        // Default format
        final DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
        // Bind
        Binding<TestBean, Date, JComponent, String> binding = TextBinding
                        .text(bean, Properties.DATE, textField, format);
        binding.bind();

        // Test
        assertEquals(null, bean.getDate());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Update the bean value
        final Date value = Calendar.getInstance().getTime();
        bean.setDate(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(format.format(value), textField.getText());
            }
        });
        // Clear the text field
        textField.setText(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getDate());
            }
        });
        // Update the text field with a value
        textField.setText(format.format(value));
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {
                    assertEquals(format.parse(textField.getText()), bean.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Clear the bean value
        bean.setDate(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals("", textField.getText());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link TextBinding#text(Object, org.jdesktop.beansbinding.Property, JLabel)} for a {@link JLabel} and
     * property of type {@link String}.
     */
    @Test
    public void testBindTextJLabelToString() {
        // Setup
        final TestBean bean = new TestBean();
        final JLabel label = new JLabel();

        // Bind
        Binding<TestBean, String, JComponent, String> binding = TextBinding.text(bean, Properties.STRING, label);
        binding.bind();

        // Test
        assertEquals(null, bean.getString());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, label.getText());
            }
        });

        // Update the bean value
        final String value = "some value";
        bean.setString(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value.toString(), label.getText());
            }
        });
        // Set the label to null (binding is read only so bean should not be updated)
        label.setText(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value, bean.getString());
            }
        });
        // Set the same value on the bean property, the label will not be updated (same value means no property change)
        bean.setString(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, label.getText());
            }
        });
        // Set a new value on the bean property
        final String newValue = "some new value";
        bean.setString(newValue);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(newValue.toString(), label.getText());
            }
        });
        // Set the bean value to null
        bean.setString(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, label.getText());
            }
        });
        // Update the label with a value (binding is read only so bean should not be updated)
        label.setText(value.toString());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getString());
            }
        });
        // Set the label to an empty string, bean property should be set to null
        label.setText("");
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getString());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test {@link TextBinding#text(Object, org.jdesktop.beansbinding.Property, JLabel)} for a {@link JLabel} and
     * property of type {@link Integer}.
     */
    @Test
    public void testBindTextJLabelToInteger() {
        // Setup
        final TestBean bean = new TestBean();
        final JLabel label = new JLabel();

        // Bind
        Binding<TestBean, Integer, JComponent, String> binding = TextBinding.text(bean, Properties.INTEGER, label);
        binding.bind();

        // Test
        assertEquals(null, bean.getIntegr());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, label.getText());
            }
        });

        // Update the bean value
        final Integer value = Integer.valueOf(2);
        bean.setIntegr(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value.toString(), label.getText());
            }
        });
        // Set the label to null (binding is read only so bean should not be updated)
        label.setText(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value, bean.getIntegr());
            }
        });
        // Set the same value on the bean property, the label will not be updated (same value means no property change)
        bean.setIntegr(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, label.getText());
            }
        });
        // Set a new value on the bean property
        final Integer newValue = Integer.valueOf(10);
        bean.setIntegr(newValue);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(newValue.toString(), label.getText());
            }
        });
        // Set the bean value to null
        bean.setIntegr(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, label.getText());
            }
        });
        // Update the label with a value (binding is read only so bean should not be updated)
        label.setText(value.toString());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getIntegr());
            }
        });
        // Set the label to an empty string, bean property should be set to null
        label.setText("");
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getIntegr());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

}
