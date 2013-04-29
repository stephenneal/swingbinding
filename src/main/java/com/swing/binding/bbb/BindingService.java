package com.swing.binding.bbb;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.PropertyStateListener;

import com.swing.binding.bbb.mvc.PresentationModel;

/**
 * Manage bindings. Provides a mechanism to release all bindings that are managed by this instance. It is also possible
 * to release bindings for a single source object. Bindings should be released to guard against memory leaks.
 * 
 * @author Stephen Neal
 * @since 11/04/2013
 */
public class BindingService {

    private Map<Object, List<Binding<?, ?, ?, ?>>> bindingMap;
    private boolean released;
    private final Object lock = new Object();

    /**
     * Default constructor.
     */
    public BindingService() {
        super();
        this.released = false;
        this.bindingMap = new HashMap<Object, List<Binding<?, ?, ?, ?>>>(50);
    }

    /**
     * Invokes {@link Binding#bind()} and adds the binding to the list of bindings to manage.
     * 
     * @param binding binding
     */
    public void bind(final Binding<?, ?, ?, ?> binding) {
        if (binding == null) {
            return;
        }
        // Synchronise to prevent binding during or after release
        synchronized (this.lock) {
            if (this.released) {
                throw new IllegalStateException("cannot add bindings after the instance is released");
            }
            Object key = binding.getSourceObject();
            List<Binding<?, ?, ?, ?>> value = this.bindingMap.get(key);
            if (value == null) {
                value = new ArrayList<Binding<?, ?, ?, ?>>(50);
                value.add(binding);
                this.bindingMap.put(key, value);
                if (key instanceof PresentationModel) {
                    // Add a listener to refresh bindings whenever property change support is enabled
                    PresentationModel.Properties.PROPERTY_CHANGE_SUPPORT_ENABLED.addPropertyStateListener((PresentationModel) key, new PropertyChangeSupportDisabledListener());
                }
            } else {
                value.add(binding);
            }
        }
        binding.bind();
    }

    /**
     * Release all bindings managed by this instance.
     */
    public void release() {
        // Synchronise to prevent binding during or after release
        synchronized (this.lock) {
            if (this.released) {
                return;
            }
            Iterator<Entry<Object, List<Binding<?, ?, ?, ?>>>> itr = this.bindingMap.entrySet().iterator();
            Entry<Object, List<Binding<?, ?, ?, ?>>> e = null;
            while (itr.hasNext()) {
                e = itr.next();
                BindingService.release(e.getKey(), e.getValue());
                itr.remove();
            }
            this.released = true;
        }
    }

    /**
     * Release all bindings for a bean instance.
     */
    public void release(Object bean) {
        if (bean == null) {
            return;
        }
        // Synchronise to prevent binding during this release
        synchronized (this.lock) {
            if (this.released) {
                return;
            }
            BindingService.release(bean, this.bindingMap.get(bean));
            this.bindingMap.remove(bean);
        }
    }

    /**
     * Release all bindings for a bean instance.
     */
    static void release(Object bean, List<Binding<?, ?, ?, ?>> bindings) {
        if (bean == null) {
            return;
        }
        BindingService.release(bindings);
        if (bean instanceof PresentationModel) {
            final PresentationModel m = (PresentationModel) bean;
            PropertyChangeListener[] listeners = m.getPropertyChangeListeners("propertyChangeSupportDisabled");
            if (listeners != null) {
                for (PropertyChangeListener l : listeners) {
                    if (l instanceof PropertyChangeSupportDisabledListener) {
                        m.removePropertyChangeListener("propertyChangeSupportDisabled", l);
                    }
                }
            }
        }
    }

    /**
     * Release a list of bindings.
     */
    static void release(List<Binding<?, ?, ?, ?>> bindings) {
        if (bindings == null) {
            return;
        }
        Iterator<Binding<?, ?, ?, ?>> itr = bindings.iterator();
        Binding<?, ?, ?, ?> b = null;
        while (itr.hasNext()) {
            b = itr.next();
            BindingService.release(b);
            itr.remove();
        }
    }

    /**
     * Release a binding.
     */
    static void release(Binding<?, ?, ?, ?> b) {
        if (b == null) {
            return;
        }
        if (b.isBound()) {
            b.unbind();
        }
        if (b.getBindingListeners() != null) {
            for (BindingListener l : b.getBindingListeners()) {
                b.removeBindingListener(l);
            }
        }
    }

    private class PropertyChangeSupportDisabledListener implements PropertyStateListener {

        @Override
        public void propertyStateChanged(PropertyStateEvent evt) {
            Boolean enabled = (Boolean) evt.getNewValue();
            if (enabled != null && enabled.booleanValue()) {
                List<Binding<?, ?, ?, ?>> bindings = BindingService.this.bindingMap.get(evt.getSource());
                if (bindings != null) {
                    for (Binding<?, ?, ?, ?> b : bindings) {
                        b.refreshAndNotify();
                    }
                }
            }
        }

    }
}
