package com.swing.binding.bbb.mvc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Property;

import com.swing.binding.PropertyChangeSupport2;
import com.swing.binding.bbb.BindingService;

/**
 * The base class for models that participate in an MVC framework that uses BetterBeansBinding to synchronise the view
 * and model. The binding requires {@link PropertyChangeSupport}. Implementing classes must be sure to fire
 * {@link PropertyChangeEvent}'s in setter's (TODO consider having an annotation for this).
 * <p>
 * To learn more about the purpose of a presentation model in an MVC framework read Martin Fowler's document
 * {@link http://martinfowler.com/eaaDev/PresentationModel.html}.
 * </p>
 * <p>
 * This also supports temporarily disabling the binding by invoking {@link #setPropertyChangeSupportDisabled(boolean)}.
 * {@link BindingService} listens for changes to this property and when it is enabled (after having been disabled) it
 * invokes {@link Binding#refreshAndNotify()} on each binding that has this bean as the source.
 * </p>
 * 
 * @author Stephen Neal
 * @since 10/04/2013
 */
public class PresentationModel {

    // Static
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Defines properties that can be bound on this model.
     */
    public static class Properties {
        public static Property<PresentationModel, String> TITLE = create("title");
        public static Property<PresentationModel, String> PROPERTY_CHANGE_SUPPORT_ENABLED = create("propertyChangeSupportEnabled");

        protected static <B extends PresentationModel, V> Property<B, V> create(String name) {
            return BeanProperty.create(name);
        }
    }

    // Instance
    // ---------------------------------------------------------------------------------------------------------------

    private transient PropertyChangeSupport propertyChangeSupport;
    private boolean propertyChangeSupportEnabled;
    private String title;

    protected PresentationModel() {
        super();
        PropertyChangeSupport2 pcs = new PropertyChangeSupport2(this);
        this.propertyChangeSupportEnabled = pcs.isEnabled();
        this.propertyChangeSupport = pcs;
    }

    /**
     * Get the value of {@code disabled}. If it is {@code true} property change events will fire.
     * 
     * @return the value of {@code disabled}.
     */
    public final boolean isPropertyChangeSupportEnabled() {
        return this.propertyChangeSupportEnabled;
    }

    /**
     * Set the value of {@code propertyChangeSupportEnabled}. If it is {@code true} property change events will fire.
     * 
     * @param newValue
     *            {@code true} to fire property change events otherwise {@code false}.
     */
    public final void setPropertyChangeSupportEnabled(boolean newValue) {
        if (!(this.propertyChangeSupport instanceof PropertyChangeSupport2)) {
            throw new UnsupportedOperationException("propertyChangeSupport does not support enabling/disabling");
        }
        synchronized (this) {
            PropertyChangeSupport2 pcs2 = (PropertyChangeSupport2) this.propertyChangeSupport;
            boolean oldValue = pcs2.isEnabled();
            this.propertyChangeSupportEnabled = newValue;
            getPropertyChangeSupport().firePropertyChange("propertyChangeSupportEnabled", oldValue, newValue);
            // Update the property change support AFTER firing the event or an event will not fire when it is disabled
            pcs2.setEnabled(newValue);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newValue) {
        String oldValue = this.title;
        this.title = newValue;
        getPropertyChangeSupport().firePropertyChange("title", oldValue, newValue);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE, false);
    }

    // PropertyChangeSupport delegate methods required for compatibility with BetterBeansBinding
    // -----------------------------------------------------------------------------------------------------------------

    protected final PropertyChangeSupport getPropertyChangeSupport() {
        return this.propertyChangeSupport;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.propertyChangeSupport.getPropertyChangeListeners();
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return this.propertyChangeSupport.getPropertyChangeListeners(propertyName);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    // PropertyChangeSupport delegate added for convenience
    // -----------------------------------------------------------------------------------------------------------------

    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void firePropertyChange(String propertyName, int oldValue, int newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
