package com.swing.binding.bbb.mvc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;

import com.swing.binding.PropertyChangeSupport2;

/**
 * The base class for models that participate in an MVC framework that uses BetterBeansBinding to synchronise the view
 * and model. The binding requires {@link PropertyChangeSupport}. Implementing classes must be sure to fire {@link PropertyChangeEvent}'s in setter's.
 * <p>
 * To learn more about the purpose of a presentation model in an MVC framework read Martin Fowler's document {@link http://martinfowler.com/eaaDev/PresentationModel.html}.
 * </p>
 * <p>
 * This also supports pausing the firing of events by invoking {@link #pausePropertyChangeSupport()}.
 * </p>
 * 
 * @author Stephen Neal
 * @since 24/04/2013
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
     * Return {@code true} if {@link PropertyChangeSupport} is paused otherwise {@code false} {@link PropertyChangeSupport} is paused. This is only supported by
     * {@link PropertyChangeSupport2}.
     * 
     * @return {@code true} if {@link PropertyChangeSupport} is paused otherwise {@code false}
     */
    public final boolean isPropertyChangeSupportPaused() {
        if (!(this.propertyChangeSupport instanceof PropertyChangeSupport2)) {
            return false;
        }
        return ((PropertyChangeSupport2) this.propertyChangeSupport).isPaused();
    }

    /**
     * Pause the underlying {@link PropertyChangeSupport}. NB. this is only supported if the underlying {@link PropertyChangeSupport} is an instance of
     * {@link PropertyChangeSupport2}.
     * 
     * @see #unpausePropertyChangeSupport()
     * @throws UnsupportedOperationException
     *             if the underlying {@link PropertyChangeSupport} is not an instance of {@link PropertyChangeSupport2}.
     */
    public final void pausePropertyChangeSupport() {
        if (!(this.propertyChangeSupport instanceof PropertyChangeSupport2)) {
            throw new UnsupportedOperationException("propertyChangeSupport does not support enabling/disabling");
        }
        synchronized (this) {
            ((PropertyChangeSupport2) this.propertyChangeSupport).setPaused(true);
        }
    }

    /**
     * Stop the pause of the underlying {@link PropertyChangeSupport}. NB. this is only supported if the underlying {@link PropertyChangeSupport} is an instance of
     * {@link PropertyChangeSupport2}.
     * 
     * @see #pausePropertyChangeSupport()
     * @throws UnsupportedOperationException
     *             if the underlying {@link PropertyChangeSupport} is not an instance of {@link PropertyChangeSupport2}.
     */
    public final void unpausePropertyChangeSupport() {
        if (!(this.propertyChangeSupport instanceof PropertyChangeSupport2)) {
            throw new UnsupportedOperationException("propertyChangeSupport does not support enabling/disabling");
        }
        synchronized (this) {
            ((PropertyChangeSupport2) this.propertyChangeSupport).setPaused(false);
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
