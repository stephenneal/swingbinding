package com.swing.binding.bbb;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Property;

/**
 * Wraps BetterBeansBinding to provide operations that bind the value of a property representing some state of a
 * {@link JComponent} to a bean property. State includes enabled, visible and editable.
 * 
 * @author Stephen Neal
 * @since 18/04/2013
 */
public class StateBinding {

    /**
     * Create a binding of a bean property to the editabe property of a {@link JTextComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding is being used for state the state should
     * only be updated via the property it is bound to.
     * </p>
     * 
     * @param bean bean to bind
     * @param propertyName name of the bean property to bind
     * @param component {@link JTextComponent} to bind
     * @return binding instance
     */
    public static <B> Binding<B, Boolean, JComponent, Boolean> editable(B bean, String propertyName,
                    JTextComponent component) {
        return state(bean, propertyName, component, "editable");
    }

    /**
     * Create a binding of a bean property to the enabled property of a {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding is being used for state the state should
     * only be updated via the property it is bound to.
     * </p>
     * 
     * @param bean bean to bind
     * @param propertyName name of the bean property to bind
     * @param component {@link JComponent} to bind
     * @return binding instance
     */
    public static <B> Binding<B, Boolean, JComponent, Boolean>
                    enabled(B bean, String propertyName, JComponent component) {
        return state(bean, propertyName, component, "enabled");
    }

    /**
     * Create a binding of a bean property to the visible property of a {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding is being used for state the state should
     * only be updated via the property it is bound to.
     * </p>
     * 
     * @param bean bean to bind
     * @param propertyName name of the bean property to bind
     * @param component {@link JComponent} to bind
     * @return binding instance
     */
    public static <B> Binding<B, Boolean, JComponent, Boolean>
                    visible(B bean, String propertyName, JComponent component) {
        return state(bean, propertyName, component, "visible");
    }

    /**
     * Create a binding of a bean property to a state property of a {@link JComponent}.
     * <p>
     * The {@link UpdateStrategy} is {@link UpdateStrategy#READ}, when binding is being used for state the state should
     * only be updated via the property it is bound to.
     * </p>
     * 
     * @param bean bean to bind
     * @param propertyName name of the bean property to bind
     * @param component {@link JComponent} to bind
     * @return binding instance
     */
    private static <B> Binding<B, Boolean, JComponent, Boolean> state(B bean, String propertyName,
                    JComponent component, String componentPropertyName) {
        Property<B, Boolean> bP = BeanProperty.create(propertyName);
        Property<JComponent, Boolean> cP = StateProperty.createState(componentPropertyName);
        return Bindings.createAutoBinding(UpdateStrategy.READ, bean, bP, component, cP);
    }
}
