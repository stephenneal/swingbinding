package com.swing.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends {@link PropertyChangeSupport} to not fire a property change event when the old and new values are {@code null}. It also allows firing of events to be disabled by setting
 * {@code enabled} to {@code false}.
 * 
 * @author Stephen Neal
 */
public class PropertyChangeSupport2 extends PropertyChangeSupport {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyChangeSupport2.class);

    private boolean enabled;

    public PropertyChangeSupport2(Object sourceBean) {
        super(sourceBean);
        this.enabled = false;
    }

    /**
     * Fire a property change event if oldValue is not equal to new value.
     */
    @Override
    public void firePropertyChange(PropertyChangeEvent evt) {
        if (isEnabled()) {
            LOGGER.info("firePropertyChange: source = " + getSimpleClassName(evt.getSource()) + " Enabled");
        } else if (ObjectUtils.equals(evt.getOldValue(), evt.getNewValue())) {
            LOGGER.debug("firePropertyChange: source = " + getSimpleClassName(evt.getSource()) + "; name = "
                            + evt.getPropertyName() + "; oldValue = newValue (" + evt.getNewValue() + "), ignored ");
        } else {
            LOGGER.info("firePropertyChange: source = " + getSimpleClassName(evt.getSource()) + "; name = "
                            + evt.getPropertyName() + "; oldValue = " + evt.getOldValue() + "; newValue = "
                            + evt.getNewValue());
            super.firePropertyChange(evt);
        }
    }

    /**
     * Get the value of {@code Enabled}. If it is {@code true} property change events will fire.
     * 
     * @return the value of {@code Enabled}.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Set the value of {@code enabled}. If it is {@code true} property change events will fire.
     * 
     * @param newValue
     *            {@code true} to enable firing property change events otherwise {@code false}.
     */
    public void setEnabled(boolean newValue) {
        this.enabled = newValue;
    }

    private static String getSimpleClassName(Object o) {
        return o == null ? null : o.getClass().getSimpleName();
    }

}
