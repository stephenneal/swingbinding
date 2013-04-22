package com.swing.binding.bbb;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.Property;

/**
 * Wraps BetterBeansBinding to provide operations that bind the value of a "text" property on a {@link JComponent} to a
 * bean property.
 * 
 * @author Stephen Neal
 * @since 18/04/2011
 */
public class TextBinding {

    /**
     * Create a binding of the bean property to the "text" property of a {@link JTextComponent}. Refer to
     * {@link #createBinding(Object, BeanProperty, JComponent)} for more information about the binding.
     * 
     * @param bean bean to bind
     * @param bP bean property to bind
     * @param component {@link JComponent} to bind (must have a "text" property)
     * @return binding instance
     */
    public static <B, V> Binding<B, V, JComponent, String> text(B bean, Property<B, V> bP, JTextComponent component) {
        return createBinding(bean, bP, component, null);
    }

    /**
     * Create a binding of the bean property to the "text" property of a {@link JLabel}. Refer to
     * {@link #createBinding(Object, String, JComponent)} for more information about the binding.
     * 
     * @param bean bean to bind
     * @param bP bean property to bind
     * @param component {@link JLabel} to bind
     * @return binding instance
     */
    public static <B, V> Binding<B, V, JComponent, String> text(B bean, Property<B, V> bP, JLabel component) {
        return createBinding(bean, bP, component);
    }

    /**
     * Create a binding of the bean property to the "text" property of a {@link JComponent}, i.e {@link JTextComponent},
     * {@link JLabel} and specify the {@link DateFormat} to use for converting {@link Date} to/from {@link String}.
     * Refer to {@link #createBinding(Object, String, JComponent)} for more information about the binding.
     * 
     * @param bean bean to bind
     * @param bP bean property to bind
     * @param component {@link JComponent} to bind (must have a "text" property)
     * @param format date format to convert {@link Date} to/from {@link String}
     * @return binding instance
     */
    public static <B> Binding<B, Date, JComponent, String> text(B bean, Property<B, Date> bP, JComponent component,
                    DateFormat format) {
        return createBinding(bean, bP, component, format);
    }

    private static <B, V> Binding<B, V, JComponent, String> createBinding(B bean, Property<B, V> bP,
                    JComponent component) {
        return createBinding(bean, bP, component, null);
    }

    /**
     * Create a binding of the bean property to the "text" property of a {@link JComponent}, i.e {@link JTextComponent},
     * {@link JLabel}. This will fail if the {@link JComponent} does not have a "text" property. The text component
     * value is updated to match the value of the property. The property type does not have to be a {@link String}, for
     * types other than {@link String} a converter is applied. BetterBeansBinding provides plenty of default conversions
     * which are hooked into the {@link Binding}, where those are not sufficient custom conversions are added.
     * <p>
     * The {@link UpdateStrategy} for a {@link JLabel} is {@link UpdateStrategy#READ}, for any other component it is
     * {@link UpdateStrategy.READ_WRITE}. A {@link JLabel} is {@link UpdateStrategy.READ} because it does not accept
     * user input and therefore cannot have its value set except via code i.e invoking {@link JLabel#setText(String)}.
     * When binding is being used for a label this should never be the case, the value should always be updated via the
     * property it is bound to. In the future an API with {@link UpdateStrategy} can be added if required.
     * </p>
     * <p>
     * NB. creates the binding but does not actually bind. It is a helper method intended for use in the {@code Binder}
     * which ensures bindings are properly managed.
     * </p>
     * 
     * @param bean bean to bind
     * @param bP bean property to bind
     * @param component {@link JComponent} to bind (must have a "text" property)
     * @return the {@link Binding}
     */
    private static <B, V> Binding<B, V, JComponent, String> createBinding(B bean, Property<B, V> bP,
                    JComponent component, DateFormat dateFormat) {
        Property<JComponent, String> cP = SwingProperty.create("text", bP.getWriteType(bean));
        UpdateStrategy us = UpdateStrategy.READ_WRITE;
        if (component instanceof JLabel) {
            us = UpdateStrategy.READ;
        }
        Binding<B, V, JComponent, String> binding = Bindings.createAutoBinding(us, bean, bP, component, cP);
        setConverter(bean, binding, dateFormat);
        return binding;
    }

    /**
     * Set a converter (if required). Custom converters are require for {@link Date} but for most other types the
     * default conversions provided by BetterBeansBinding are sufficient. The default conversions can be found in
     * {@link Converter}.
     */
    private static <B, V> void setConverter(B bean, Binding<B, V, JComponent, String> binding, DateFormat dateFormat) {
        Class<?> writeType = binding.getSourceProperty().getWriteType(bean);
        if (Date.class.isAssignableFrom(writeType)) {
            @SuppressWarnings("unchecked")
            Binding<B, Date, JComponent, String> dB = (Binding<B, Date, JComponent, String>) binding;
            if (dateFormat == null) {
                dateFormat = DateFormat.getDateInstance();
            }
            dB.setConverter(Converter2.newDateString(dateFormat));
        }
    }

}
