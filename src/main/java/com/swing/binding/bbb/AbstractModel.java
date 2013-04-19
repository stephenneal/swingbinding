package com.swing.binding.bbb;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jdesktop.beansbinding.Binding;

import com.swing.binding.PropertyChangeSupport2;

/**
 * Model classes to be used in binding can extend this to get {@link PropertyChangeSupport} for compatibility with
 * BetterBeansBinding. Implementing classes must be sure to fire {@link PropertyChangeEvent}'s in setter's.
 * <p>
 * This also supports temporarily disabling binding via {@link #setPropertyChangeSupportDisabled(boolean)}.
 * {@link BindingService} listens for changes to this property and when it is enabled (after having been disabled) it
 * invokes {@link Binding#refreshAndNotify()} on each binding that has this bean as the source.
 * </p>
 * 
 * @author Stephen Neal
 * @since 10/04/2013
 */
public class AbstractModel {

    private transient PropertyChangeSupport propertyChangeSupport;
    private String title;

    protected AbstractModel() {
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
}
