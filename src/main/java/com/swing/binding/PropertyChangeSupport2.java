package com.swing.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends {@link PropertyChangeSupport} to not fire a property change event when the old and new values are {@code null}. It also allows firing of events to be paused by setting
 * {@code paused} to {@code true}.
 * 
 * @author Stephen Neal
 */
public class PropertyChangeSupport2 extends PropertyChangeSupport {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyChangeSupport2.class);

    private boolean paused;
    private final ConcurrentLinkedQueue<PropertyChangeEvent> pausedEventQueue;

    public PropertyChangeSupport2(Object sourceBean) {
        super(sourceBean);
        this.paused = false;
        this.pausedEventQueue = new ConcurrentLinkedQueue<PropertyChangeEvent>();
    }

    /**
     * Fire a property change event if oldValue is not equal to new value.
     */
    @Override
    public void firePropertyChange(PropertyChangeEvent evt) {
        if (isPaused()) {
            LOGGER.info("firePropertyChange: PAUSE property change (disabled); source = " + getSimpleClassName(evt.getSource()) + "; property = "
                    + evt.getPropertyName() + "; oldValue = " + evt.getOldValue() + "; newValue = "
                    + evt.getNewValue());
            this.pausedEventQueue.add(evt);
        } else if (ObjectUtils.equals(evt.getOldValue(), evt.getNewValue())) {
            LOGGER.info("firePropertyChange: IGNORE property change (new and old values equal); source = " + getSimpleClassName(evt.getSource()) + "; property = "
                    + evt.getPropertyName() + "; oldValue = newValue (" + evt.getNewValue() + ")");
        } else {
            LOGGER.info("firePropertyChange: FIRE source = " + getSimpleClassName(evt.getSource()) + "; property = "
                    + evt.getPropertyName() + "; oldValue = " + evt.getOldValue() + "; newValue = "
                    + evt.getNewValue());
            super.firePropertyChange(evt);
        }
    }

    /**
     * Get the value of {@code paused}. If it is {@code false} property change events will fire.
     * 
     * @return the value of {@code paused}.
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Set the value of {@code paused}. When paused events are not fired but added to a queue, when no longer paused all queued events are fired.
     * 
     * @param newValue
     *            {@code true} to pause firing property change events otherwise {@code false}.
     */
    public void setPaused(boolean newValue) {
        synchronized (this) {
            this.paused = newValue;
            if (!this.paused && !this.pausedEventQueue.isEmpty()) {
                PropertyChangeEvent e = this.pausedEventQueue.poll();
                while (e != null) {
                    firePropertyChange(e);
                    e = this.pausedEventQueue.poll();
                }
            }
        }
    }

    private static String getSimpleClassName(Object o) {
        return o == null ? null : o.getClass().getSimpleName();
    }

}
