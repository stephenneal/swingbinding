package com.swing.binding.bbb;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Property;

/**
 * Wraps BetterBeansBinding to provide operations that bind the value of a
 * property representing some state of a {@link JComponent} to a bean property.
 * State includes enabled, visible and editable.
 * 
 * @author Stephen Neal
 * @since 18/04/2013
 */
public class StateBinding {
    /**
     * Create a binding of a bean property to the editable property of a
     * {@link JTextComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding
     * is being used for state the state should only be updated via the property
     * it is bound to.
     * </p>
     * 
     * @param bean
     *            bean to bind
     * @param bP
     *            bean property to bind
     * @param component
     *            {@link JTextComponent} to bind
     * @return binding instance
     */
    public static <B> Binding<B, Boolean, JTextComponent, Boolean> editable(B bean, Property<B, Boolean> bP,
            JTextComponent component) {
        return booleanState(bean, bP, component, "editable");
    }

    /**
     * Create a binding of a bean property to the editable property of a
     * {@link JComboBox}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding
     * is being used for state the state should only be updated via the property
     * it is bound to.
     * </p>
     * 
     * @param bean
     *            bean to bind
     * @param bP
     *            bean property to bind
     * @param component
     *            {@link JComboBox} to bind
     * @return binding instance
     */
    @SuppressWarnings("rawtypes")
    public static <B> Binding<B, Boolean, JComboBox, Boolean> editable(B bean, Property<B, Boolean> bP,
            JComboBox component) {
        return booleanState(bean, bP, component, "editable");
    }

    /**
     * Create a binding of a bean property to the enabled property of a
     * {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding
     * is being used for state the state should only be updated via the property
     * it is bound to.
     * </p>
     * 
     * @param bean
     *            bean to bind
     * @param bP
     *            bean property to bind
     * @param component
     *            {@link JComponent} to bind
     * @return binding instance
     */
    public static <B> Binding<B, Boolean, JComponent, Boolean> enabled(B bean, Property<B, Boolean> bP,
            JComponent component) {
        return booleanState(bean, bP, component, "enabled");
    }

    /**
     * Create a binding of a bean property to the visible property of a
     * {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding
     * is being used for state the state should only be updated via the property
     * it is bound to.
     * </p>
     * 
     * @param bean
     *            bean to bind
     * @param bP
     *            bean property to bind
     * @param component
     *            {@link JComponent} to bind
     * @return binding instance
     */
    public static <B> Binding<B, Boolean, JComponent, Boolean> visible(B bean, Property<B, Boolean> bP,
            JComponent component) {
        return booleanState(bean, bP, component, "visible");
    }

    /**
     * Create a binding of a bean property to the foreground property of a
     * {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding
     * is being used for state the state should only be updated via the property
     * it is bound to.
     * </p>
     * 
     * @param bean
     *            bean to bind
     * @param bP
     *            bean property to bind
     * @param component
     *            {@link JComponent} to bind
     * @return binding instance
     */
    public static <B> Binding<B, Color, JComponent, Color> foreground(B bean, Property<B, Color> bP,
            JComponent component) {
        return state(bean, bP, component, "foreground");
    }

    /**
     * Create a binding of a bean property to a state property of a
     * {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding
     * is being used for state the state should only be updated via the property
     * it is bound to.
     * </p>
     * 
     * @param bean
     *            bean to bind
     * @param bP
     *            bean property to bind
     * @param component
     *            {@link JComponent} to bind
     * @return binding instance
     */
    private static <B, C extends JComponent> Binding<B, Boolean, C, Boolean> booleanState(B bean,
            Property<B, Boolean> bP, C component, String componentPropertyName) {
        Property<C, Boolean> cP = StateProperty.createState(componentPropertyName);
        return Bindings.createAutoBinding(UpdateStrategy.READ, bean, bP, component, cP);
    }

    /**
     * Create a binding of a bean property to a state property of a
     * {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding
     * is being used for state the state should only be updated via the property
     * it is bound to.
     * </p>
     * 
     * @param bean
     *            bean to bind
     * @param bP
     *            bean property to bind
     * @param component
     *            {@link JComponent} to bind
     * @return binding instance
     */
    private static <B, V> Binding<B, V, JComponent, V> state(B bean, Property<B, V> bP, JComponent component,
            String componentPropertyName) {
        Property<JComponent, V> cP = BeanProperty.create(componentPropertyName);
        return Bindings.createAutoBinding(UpdateStrategy.READ, bean, bP, component, cP);
    }
}
