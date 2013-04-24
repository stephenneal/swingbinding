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

        protected static <B extends PresentationModel, V> Property<B, V> create(String name) {
            return BeanProperty.create(name);
        }
    }

    // Instance
    // ---------------------------------------------------------------------------------------------------------------

    private transient PropertyChangeSupport propertyChangeSupport;
    private String title;

    protected PresentationModel() {
        super();
        this.propertyChangeSupport = new PropertyChangeSupport2(this);
    }

    /**
     * Get the value of {@code disabled}. If it is {@code true} property change events will fire.
     * 
     * @return the value of {@code disabled}.
     */
    public final boolean isPropertyChangeSupportDisabled() {
        if (!(this.propertyChangeSupport instanceof PropertyChangeSupport2)) {
            throw new UnsupportedOperationException("propertyChangeSupport does not support enabling/disabling");
        }
        return ((PropertyChangeSupport2) this.propertyChangeSupport).isDisabled();
    }

    /**
     * Set the value of {@code disabled}. If it is {@code true} property change events will fire.
     * 
     * @param disabled {@code true} to disable firing property change events otherwise {@code true}.
     */
    public final void setPropertyChangeSupportDisabled(boolean newValue) {
        if (!(this.propertyChangeSupport instanceof PropertyChangeSupport2)) {
            throw new UnsupportedOperationException("propertyChangeSupport does not support enabling/disabling");
        }
        synchronized (this) {
            PropertyChangeSupport2 pcs2 = (PropertyChangeSupport2) this.propertyChangeSupport;
            boolean oldValue = pcs2.isDisabled();
            pcs2.setDisabled(newValue);
            // Whenever it is re-enabled, notify any listeners
            if (newValue) {
                getPropertyChangeSupport().firePropertyChange("propertyChangeSupportDisabled", oldValue, newValue);
            }
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

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, int oldValue, int newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
